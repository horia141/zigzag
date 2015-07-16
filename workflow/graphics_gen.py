import argparse


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--android_output_dir_path', type=str, required=True,
       help='Output directory for Android graphics. Will be cleared')
    parser.add_argument('--ios_output_dir_path', type=str, required=True,
       help='Output directory for iOS graphics. Will be cleared')
    args = parser.parse_args()

    print args.android_output_dir_path
    print args.ios_output_dir_path


if __name__ == '__main__':
    main()
