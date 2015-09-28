# Project 2 - *Grid Image Search*

**Grid Image Search** is an android app that allows a user to search for images on web using simple filters. The app utilizes [Google Image Search API](https://developers.google.com/image-search/). Please note that API has been officially deprecated as of May 26, 2011.

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

* [ ] User can **search for images** by specifying a query and launching a search. Search displays a grid of image results from the Google Image API.
* [ ] User can click on "settings" which allows selection of **advanced search options** to filter results
* [ ] User can configure advanced search filters such as:
  * [ ] Size (small, medium, large, extra-large)
  * [ ] Color filter (black, blue, brown, gray, green, etc...)
  * [ ] Type (faces, photo, clip art, line art)
  * [ ] Site (espn.com)
* [ ] Subsequent searches have any filters applied to the search results
* [ ] User can tap on any image in results to see the image **full-screen**
* [ ] User can **scroll down to see more images**. The maximum number of images is 64 (limited by API).

The following **optional** features are implemented:

* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/rkgra/grid_image_search/blob/master/Google_Image_Search_demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [2015] [Ram]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
