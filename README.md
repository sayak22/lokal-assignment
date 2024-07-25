# Lokal Assignment

**Lokal Assignment** is an Android application designed to help users discover job opportunities and save their favorites for later viewing. The app features a clean and minimalistic design with a simple user interface for easy navigation.

## Features

- **Job Listings**: Browse through a list of job opportunities with essential details like title, location, salary, and contact information.
- **Infinite Scrolling**: Automatically load more job listings as you scroll down the list.
- **Job Details**: Tap on a job card to view more detailed information about the job.
- **Bookmarks**: Save jobs to your bookmarks for easy access later. Bookmarking is available offline.
- **Offline Access**: Access bookmarked jobs even when offline.

## Technical Details

- **Retrofit**: Used for making API calls to fetch job listings and details.
- **RecyclerView**: Used to display job listings in a scrollable list with infinite scrolling.
- **MVVM Architecture**: Implemented to separate concerns and manage UI-related data efficiently.
- **Room Database**: Used to store bookmarked jobs locally for offline access.
- **LiveData**: Employed to observe data changes and update the UI accordingly.

## How to Use

1. **Home Screen**: Upon opening the app, you will be presented with a list of job opportunities in the "Jobs" section.
2. **Browsing Jobs**: Scroll through the list to view available jobs. More jobs are loaded automatically as you reach the end of the list.
3. **Viewing Job Details**: Tap on a job to view more details about the position, including a detailed description and contact information.
4. **Bookmarking Jobs**: To bookmark a job, simply tap the bookmark icon on the job card. Bookmarked jobs will appear in the "Bookmarks" section.
5. **Accessing Bookmarks**: Navigate to the "Bookmarks" section using the bottom navigation bar to view your saved jobs.

## Installation

To use the Lokal Assignment app, you can download the APK from the [Releases section](https://github.com/your-repo/releases) of this GitHub repository and install it on your Android device. Ensure that you allow installations from unknown sources if prompted.

## Contributing

We welcome contributions to improve the Lokal Assignment app. If you have any suggestions, bug reports, or feature requests, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

We would like to thank all contributors and users who have supported this project.
