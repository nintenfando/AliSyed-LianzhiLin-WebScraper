# Noti
### 2025 CLHS Cybersecurity
By Ali Syed and Lianzhi Lin

## WebScraping and Web Cracking
Using a program/bots to take data from websites; the data type does not matter - extracting text, images, code, etc. - are all forms of web scraping.

## Description
Looks at the latest message you've recieved on Gmail.

## Usage
Run it on Cmd or Visual Studio (recommended).

## Sources

StackOverflow, How do I resolve ClassNotFoundException?, 1 July 2013, https://stackoverflow.com/questions/17408769/how-do-i-resolve-classnotfoundexception
 
CodeAcademy. How to Send Emails Using Gmail API. YouTube, 31 July 2023, https://youtu.be/xtZI23hxetw?si=cqhZHVF1cUA6u-eR.

Google Workspace, Gmail API Overview, Google, 7 May 2025, https://developers.google.com/workspace/gmail/api/guides

## Requirement Checklist
_Feel free to add any checklist items below to best communicate with your group_
[Instructions for how to use GitHub text formatting] (https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax) 

_To mark a box as complete enter an X in between the brackets. See the first check for an example_
- [x] Created github repository
- [x] edited MarkDown file for my group with project name and group members.
- [x] Research and define webscraping and web cracking in md file
- [x] Decide Project using research and personal preference. Fill in Description on md file when ready.
- [x] Code and Create Project
- [x] Presentation
   - [x] code snippets
   - [x] video of running program
   - [x] At least 11 slides **_See Instructions_**
- [x] Project Write-Up
- [x] Works Cited

## Writeup
When we began our project, we knew Noti would need access to our Gmail account. The first step towards that was getting the Gmail API. All I needed to do was just go to the Google APIs site and follow along with their steps. I agreed to their terms and such, and eventually got access to my credentials.JSON, which is my API key, something my program will need to get access to my Gmail's inbox. We then began research, finding YouTube videos and website tutorials on how to use the API, most importantly the methods. 

Once we found the methods, we narrowed down our project to just reading our most recent message, and if we had more time then we would allow the user to send messages as well. In the end, although we found the send method, we didn't have enough time to implement it. However, the message methods were found and implemented, as we'd guessed it probably be faster to read messages then send them, though we didn't put much research into sending messages.

After we gathered our research, we began writing our code. We had many, many errors, but I had heard from my classmates that they were also experiencing such troubles without it hindering their program, so I decided not to think about it for the time being since our code wasn't quite ready to run yet. On the final day, we had our code ready - or so I thought - but when we ran it we came across an error. java.lang.ClassNotFoundException. Apparently we were missing a file (specifically a JAR file) that the imports needed, though I didn't know which or even where to get them. I likely skipped over it when I was reading the documentation or watching the video, but I had no idea what to do from there. With not much time on the clock, I decided to use my last resort: presenting our failed project. In the end, we learned that making something of quality takes preparation and time, both of which we expended a large quantity of, and I hope I can continue to keep that in consideration when creating projects in the future.
