# Image Voter

This little tool allows you to select your favourite image from a folder of many images.

It is intented to be used with the output of AI image generation like Stable Diffusion, but can be used for anything where you need to compare images to each other.

## Usage

![image](https://user-images.githubusercontent.com/1485873/190935015-34bb4bf3-15bc-472f-ad62-810298faaa16.png)

To get started, select a folder (or enter a folder path manually). Then click "Scan for images". This may take a couple of seconds, then the number of found images (jpg and png) is displayed:

![image](https://user-images.githubusercontent.com/1485873/190877105-533d49a1-386b-47aa-8041-b92ff3b78c43.png)

If you select "Automatically move excluded files", images you use the "exclude" button on are automatically moved into the target folder. If you do not select this option, no target folder needs to be set.

The "Sudden Death" open makes it so that images are eliminated instantly if they fail to get a vote. They are not excluded, just taken out of the rotation. They will still be shown in the result list if they got at least one vote, but their score will be lower then 100%. It will be 500 if they got eliminated in the first round, 666 in the second round, 750 in the third, and so on.

Now you can start to vote on them by clicking "Compare images".

![image](https://user-images.githubusercontent.com/1485873/190877194-d4c52f56-6589-4a4b-9b4e-d7e9f2caf239.png)

The program will display pairs of images for you to vote. You can "Like" either image (either with its button, by clicking the image, or with a hotkey) and your vote will be counted for that one and against the other. If an image is bad you can click its "Exclude" button (or right-clik it or use a hotkey) to remove it from the list of images.

At the bottom you can see some statistics. As the number of pairings can be really high, I suggest you vote at least until "Unrated images" is 0 and "Top rated" (i.e. the number of images who have the same rating) is a low number.

At any time you wish, you can click "Show ratings..." to see the partial result. When all possible pairs of images have been rated, the result screen opens automatically:

![image](https://user-images.githubusercontent.com/1485873/190877532-5d4b1440-54f3-4e85-b2ec-cd14af3c3a9f.png)

Note: I didn't run through all images for this screenshot, so all displayed entries have the same "100%" rating.

You can view the results as a (sortable) table or as pure text. When you click "Save..." the results are saved as either a CSV or a TXT file, depending on which tab is currently shown.

You can hover over the rows in the table to see a preview of the file.

## Installation

Install Java version 8 (or higher). Download the jar file from the release (on the right side -> ), double-click it.
