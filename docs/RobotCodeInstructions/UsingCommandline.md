# Simple Command Line operations and Git usage

Starting in the 2019 season, there's a stronger need to use the command-line than in previous years.  Command line interfaces are used often in real world Engineering and Software Development, so learning it is very useful.

## Opening CMD and Navigating to a directory in Windows
(Note that the first few steps in the instructions are different in Mac/Unix/Linux - please use the internet to figure out specific instructions for your non-Windows operating system.  In Linux/Unix you are looking for a bash or shell window, on Mac you are looking for the Terminal.)  Press the start button (or the Windows key on your keyboard) and type "cmd" and open Command Prompt.  This will open Command Prompt (cmd) scoped to your user home directory (typically "```C:\Users\username\```").

You will need to navigate around in order to do anything useful.  To look at the contents of your current directory, type "```dir```" ("```ls```" on Mac/Linux/Unix).  To navigate to another directory, use the change directory command ("```cd```") and type "```cd directory```".  While using this command, you can use "```..```" to reference the directory above your current scope, and "```.```" to reference the current directory.  You can also use a full name of the directory, such as "```cd C:\Users\username\git\```" to navigate to that directory.

## Simple git operations in Command Prompt
1. "```git status```" command will tell you all of the files that are different than what has been committed.
2. "```git resotre filename```" command will get rid of any changes to the specified file in your working directory and replace it with the last-committed version of that file in the local repository.
3. "```git add -A```" command will add all of the currently-changed files in your working directory to be staged and ready to commit.
4. "```git commit -m "message"```" command will commit all of the currently staged changes with the provided message.
5. "```git push```" command will push commits from your local repository to the remote repository (you will need to run "```git push origin branchname```" the first time you are pushing a new branch).
6. "```git branch```" command will show you what branches currently exist for the current repository.
7. "```git branch -c master branchname```" command will create a new topic branch off of the master branch, and "```git checkout -b branchname```" command will create a new topic branch off of hte current branch.
8. "```git pull```" command will update your local repository with changes that have been pushed to the remote repository.
9. "```git checkout branchname```" command will switch your working directory to look at a different branch.
10. "```git clone https://github.com/irs1318dev/Fauxbot.git```" command will clone the repository tracked at the provided url, creating a local copy that you can use to make changes.

For more information about Git in command prompt, look here:
[GitHub's Git cheat-sheet](https://services.github.com/on-demand/downloads/github-git-cheat-sheet/)
[GitHub's Git Handbook](https://guides.github.com/introduction/git-handbook/)
