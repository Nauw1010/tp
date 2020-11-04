---
layout: page
title: Lin Geyu's Project Portfolio Page
---

## Project: IntelliJournal

IntelliJournal is a desktop contacts/journal management application designed for
businesspersons to keep track of their contacts information as well as their meetings
with them. The user interacts with it using a CLI, and it has a GUI created with
JavaFX. It is written in Java, and has about 15 kLoC. The project is developed based
on a previous project named AddressBook Level 3.

Given below are my contributions to the project:

* **New Feature**: Support command alias (`alias` command)
   * What it does: Allow user to type faster after being familiar with the system with an alias to existing command.
   * Justification: This feature improves the user experience by not forcing user to type out the full command when they are familiar with the system.

* **New Feature**: Support delete alias (`release` command)
   * What it does: Allow user to delete an alias that is set previously.

* **Backend Work**: Maintain model to support multiple feature that `Logic` component uses.
   * What it does: Support methods that logic component needs to use in `Model` interface to maintain a good abstraction.

* **Backend Work**: Support storage for command alias
    * What it does: Store the preference of user's alias as json so that the system can keep the customized alias for users.
    * Justification: This work is important because it allows user to use command alias more naturally.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=W17&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=Lingy12&tabRepo=AY2021S1-CS2103T-W17-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Managed releases `v1.2.1` on GitHub
  * Maintain the workflow using issue tracker on Github
  * Set up milestone `v1.1` - `v1.4`

* **Enhancements to existing features**:
  * Wrote additional tests for existing features to increase coverage.

* **Community**
  * Do regular user testing to ensure the product quality.
  * Contribute user stories to start the project.
  * Report bug or design issue to teammates to ensure code quality.

* **Documentation**:
  * User Guide:
    * Added documentation for the features `alias` and `release` (Delete alias).
  * Developer Guide:
    * Added implementation details of the `alias` feature.
    * Write sample use cases in appendix.

