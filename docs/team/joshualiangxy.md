---
layout: page
title: Joshua Liang's Project Portfolio Page
---

## Project: IntelliJournal

IntelliJournal is a desktop contacts/journal management application designed for
businesspersons to keep track of their contacts information as well as their
meetings with them. The user interacts with it using a CLI, and it has a GUI
created with JavaFX. It is written in Java, and has about 15 kLoC. The project
is developed based on a previous project named AddressBook Level 3.

Given below are my contributions to the project.

* **Team role**: In charge of code quality and storage.

* **New Feature**: Added the ability to check the schedule of a given date
                   (`check` command).
  * What it does: Allows the user to filter out a list of journal entries on a
                  date given by the user.
  * Justification: Users might need to check thier schedule for a given workday.

* **Backend Work**: Support storage for journal entries.
  * What it does: Stores the user's journal entries as json so that the user
                  can look at his journal entries in the future.
  * Justification: This work is important as users would want to use
                   IntelliJournal multiple times, and this would allow the
                   journal entries to persist over multiple uses.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=joshualiangxy&tabRepo=AY2021S1-CS2103T-W17-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Managed the weekly project meetings.

* **Enhancements to existing features**:
  * Refactored tests for existing features to make tests more readable.
    (Pull request [\#43](https://github.com/AY2021S1-CS2103T-W17-4/tp/pull/43))
  * Added uuid for storage of contacts involved in journal entries. This allows
    for journal entries to be stored in a much more concise manner.
    (Pull request [\#66](https://github.com/AY2021S1-CS2103T-W17-4/tp/pull/66))

* **Documentation**:
  * User Guide:
    * Created the skeleton of the User Guide [\#156](https://github.com/AY2021S1-CS2103T-W17-4/tp/pull/156).
    * Added documentation for the feature `check`.
  * Developer Guide:
    * Added implementation details of the `check` feature.

* **Community**:
  * PRs reviewed (with non-trivial review comments):
    [\#135](https://github.com/AY2021S1-CS2103T-W17-4/tp/pull/135),
    [\#173](https://github.com/AY2021S1-CS2103T-W17-4/tp/pull/173),
    [\#210](https://github.com/AY2021S1-CS2103T-W17-4/tp/pull/210),
    [\#212](https://github.com/AY2021S1-CS2103T-W17-4/tp/pull/212)
  * Reported bugs and suggestions for other teams in the class:
    [\#230](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/230),
    [\#231](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/231),
    [\#232](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/232),
    [\#233](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/233),
    [\#234](https://github.com/AY2021S1-CS2103T-T12-1/tp/issues/234)

