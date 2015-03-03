"""Functions for interacting with message queues - sending and receiving messages."""

import pika


class ApiError(Exception):
    """Generic error raised by an API function while trying to send a request."""
    pass


class ServerError(Exception):
    """Generic error raised by a server while processing a request."""
    pass


def send_to(queue_host, queue_name, message_data):
    """Send a message to a named queue running on a given host. The message is arbitrary.

    A connection is opened to the queue, a "durable" message is posted, and then the connection
    is closed. This isn't meant to operate over long-living connections.

    The queue type is AMPQ.

    Params:
      queue_host: the hostname of the host which runs the AMPQ broker.
      queue_name: the name of the queue to post the message to.
      message_data: the message to send.
    """

    assert isinstance(queue_host, basestring)
    assert isinstance(queue_name, basestring)
    assert isinstance(message_data, basestring)

    task_queue_connection = pika.BlockingConnection(pika.ConnectionParameters(queue_host))
    task_queue_channel = task_queue_connection.channel()
    task_queue_channel.queue_declare(queue=queue_name, durable=True)
    task_queue_channel.basic_publish(exchange='', routing_key=queue_name, body=message_data,
                                     properties=pika.BasicProperties(delivery_mode=2))
    task_queue_connection.close()


class Server(object):
    """Handle messages received from a given queue. Baseclass for actual serves.

    Do not worry too much if can't process them correctly. Calling the start method will initiate
    the message handling loop. Superclasses must define a _handleMessage function which is invoked
    when a message is received.

    The queue type is AMPQ.
    """

    def __init__(self, queue_host, queue_name):
        """Create a message handling server.

        Args:
          queue_host: the hostname of the host which runs the AMPQ broker.
          queue_name: the name of the queue to receive the messages from.
        """

        assert isinstance(queue_host, basestring)
        assert isinstance(queue_name, basestring)

        self._queue_host = queue_host
        self._queue_name = queue_name
        self._connection = None
        self._channel = None

    def start(self):
        """Start the message handling server."""

        # Prepare connection to queue
        self._connection = pika.BlockingConnection(pika.ConnectionParameters(host=self._queue_host))

        self._channel = self._connection.channel()
        self._channel.queue_declare(queue=self._queue_name, durable=True)
        self._channel.basic_qos(prefetch_count=1)
        self._channel.basic_consume(self._message_callback, queue=self._queue_name)

        # Start consuming messages off the queue
        self._channel.start_consuming()

    def _message_callback(self, channel, method, properties, message):
        """Callback invoked when a message is received.

        Calls _handle_message and deals (very badly) with errors.

        Params:
          channel:
          method:
          properties:
          message: the message data.
        """

        try:
            self._handle_message(message)
        except Exception as e:
            print e
            print 'There was a problem!'
            print 'Still acknowledging .. probably should not do it'

        channel.basic_ack(delivery_tag=method.delivery_tag)

    def _handle_message(self, body):
        """Perform the processing for when a message arrives.

        This must be overridden by superclasses.

        Params:
          message: the message data.
        """
        pass
