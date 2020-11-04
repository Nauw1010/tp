---
layout: page
title: User Guide
---

* Table of Contents
{:toc}

---

## Introduction

IntelliJournal is a **desktop app for managing journals as well as contacts,
optimised for use via a Command Line Interface** (CLI) while still having the
benefits of a Graphical User Interface (GUI). Made for business people,
if you can type fast, IntelliJournal can help you record down the
details of your business meetings as journal entries with your business contacts
as well as location information faster than traditional GUI apps.

---

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `intellijournal.jar` from
   [here](https://github.com/AY2021S1-CS2103T-W17-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your
   IntelliJournal.

1. Double-click the file to start the app.

1. The GUI similar to the below should appear in a few seconds. The app will
   start with the dashboard tab, which shows you the list of recent contacts and
   the list of frequent contacts.<br>
   ![Ui](images/Ui-dashboard.png)

1. You can also navigate to the other two tabs, which will look like the second
   and third screenshots below. There will be sample contacts and journal
   entries on your initial start-up of the app.<br>
   ![Ui](images/Ui-contacts.png)
   ![Ui](images/Ui-journal.png)

1. Type the command in the command box and press Enter to execute it.
   e.g. typing `help` and pressing Enter will open the help window.<br>

   Some example commands you can try:

   * `listc` : Lists all contacts.

   * `addc n/John Doe p/98765432 e/johnd@example.com a/John street, block
     123, #01-01` :
     <br>Adds a contact named `John Doe` to the Address Book.

   * `deletec 3` : deletes the 3rd contact shown in the current list.

   * `clearc` : Clear all sample contacts.

   * `clearj` : Clear all sample journal entries.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

---

## Features

The features section will be split up according to the 3 types of features in
IntelliJournal:
1. Contacts        - features involving your address book contacts
1. Journal Entries - features involving your journal entries
1. Miscellaneous   - all other features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addcontact n/NAME`, `NAME` is a parameter which can be used as
       `add n/John Doe`.<br>

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.<br>

* Items with `…​` after them can be used multiple times including zero
  times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`,
       `t/friend t/family` etc.<br>

* If multiple items without `…​` after them are supplied by the user, the
  last item is used.<br>
  e.g. `n/NAME p/PHONE` if used as `n/John Doe n/Betsy Crowe p/91111111 p/99988877`,
       `Betsy Crowe` and `99988877` will be the name and phone numbers used.<br>

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME`
       is also acceptable.<br>

</div>

### Contacts

#### Adding a contact: `addcontact`

Adds a contact to the address book, after adding the contact, the app will show the
`Contacts` tab, and the sidebar will scroll to the new contact you just added,
displaying contact information on the right.

Aliases: `addcontact`, `addc`

Format: `addcontact n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

<div markdown="block" class="alert alert-primary">

:bulb: **Tips:**<br>

* A contact can have any number of tags (including 0).
* `TAG` must be alphanumeric, without spaces.

</div>

Examples:

* `addcontact n/John Doe` Adds a contact with the name of `John Doe`.
* `addcontact n/Betsy Crowe t/client t/important` Adds a contact with the name
   of `Betsy Crowe` and tags of `client` and `important`.

#### Listing all contacts: `listc`

Lists all the contacts in the address book.

Aliases: `listc`, `lc`

Format: `listc`

#### Editing a contact: `editc`

Edits an existing contact in the address book.

Aliases: `editc`, `edc`

Format: `editc INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

<div markdown="block" class="alert alert-primary">

:bulb: **Tips:**<br>

* Edits the contact at the specified `INDEX`. The index refers to the index
  number shown in the displayed contact list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* `TAG` must be alphanumeric, without spaces.
* When editing tags, the existing tags of the contact will be removed
  i.e adding of tags is not cumulative.
* You can remove all the contact’s tags by typing t/ without specifying any tags
  after it.
* After editing a contact, the list displayed in the `Contacts` tab will be
  reset to show all existing contacts, because the previous filtering operation
  may not still have the same effects on the edited contact.

</div>

Examples:

* `editc 1 p/91234567 e/johndoe@example.com` Edits the phone number and email
   address of the 1st contact to be `91234567` and `johndoe@example.com`
   respectively.
* `editc 2 n/Betsy Crower t/` Edits the name of the 2nd contact to be
  `Betsy Crower` and clears all existing tags.

#### Viewing a contact: `view in/c`

Opens up a contact to show further details.

Aliases: `view`, `v`

Format: `view in/c index/INDEX`

* `INDEX` refers to the index number of the contact shown in the list.

Examples:

* `view in/c index/4` Views the 4th contact in the address book.
* `view in/c index/8` Views the 8th contact in the address book.

#### Finding contacts: `find in/c`

Finds a list of contacts or journal entries that satisfy the requirements
on particular fields given by the user.

Aliases: `find`, `f`

Format: `find in/c [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS] [e/EMAIL_KEYWORDS]
        [a/ADDRESS_KEYWORDS] [t/TAG]…​`

<div markdown="block" class="alert alert-primary">

:bulb: **Tips:**<br>

* `TAG` must be alphanumeric, without spaces.
* Note that for `TAG`, IntelliJournal searches for the exact same tag instead of
  tags containing any part of the searched terms.

</div>

Examples:

* `find in/c n/Alice` Finds all contacts whose name contains `Alice`.
* `find in/c n/Alice p/65` Find all contacts whose name contains `Alice` and
  phone number contains `65`.
* `find in/c n/Alice p/65 e/@u.nus.edu a/RC4 t/Student`<br>
  Find all contacts whose name contains `Alice` and phone number contains `65`
  and email contains `@u.nus.edu` and address name contains `RC4` and tagged
  `Student`.<br>

#### Deleting a contact: `deletec`

Deletes a contact from the address book.

Aliases: `deletec`, `delc`

Format: `deletec INDEX`

* `INDEX` refers to the index number of the contact in the list.

Examples:

* `deletec 4` Deletes the 4th contact in the address book.
* `deletec 8` Deletes the 8th contact in the address book.

#### Clearing the address book: `clearc`

Clears all contacts in the addressbook.

Aliases: `clearc`, `cc`

Format: `clearc`

### Journal Entries

#### Adding a journal entry: `addjournal`

Adds a journal entry to the journal. After adding the journal entry, the app will
move to the `Journal` tab, scrolling to the entry you just added, displaying the
information of that entry on the right.

Aliases: `addjournal`, `addj`

Format: `addjournal n/TITLE [at/DATE_AND_TIME] [d/DESCRIPTION]
         [with/CONTACT_NAME]…​ [t/TAG]…​`

<div markdown="block" class="alert alert-primary">

:bulb: **Tips:**<br>

* A journal entry can have any number of contacts or tags (including 0)
* `CONTACT_NAME` must be an existing name in the address book.
* `DATE_AND_TIME` must be in the format: "YYYY-MM-DD HH:MM".
* `TAG` must be alphanumeric, without spaces.

</div>

Example:

1. Type `addjournal n/Meeting with client at/2020-09-20 14:00 d/Tea` into the
  command box and press enter.

    ![addjournal example](images/UGExamples/addjournalExample1.png)

1. You will be directed to the journal tab, with the new journal entry on the
  screen

    ![addjournal example 2](images/UGExamples/addjournalExample2.png)

#### Listing all journal entries: `listj`

Lists all journal entries in the journal.

Aliases: `listj`, `lj`

Format: `listj`

Example:

1. Type `listj` into the command box and press enter.

    ![listj example](images/UGExamples/listjExample1.png)

1. You will be directed to the journal tab, with all the journal entries listed
   on the sidebar.

    ![listj example 2](images/UGExamples/listjExample2.png)

#### Editing a journal entry: `editj`

Edits an existing entry in the journal.

Aliases: `editj`, `edj`

Format: `editj INDEX [n/TITLE] [at/DATE_AND_TIME] [d/DESCRIPTION]
         [with/CONTACT_NAME]…​ [t/TAG]…​`

<div markdown="block" class="alert alert-primary">

:bulb: **Tips:**<br>

* Edits the contact at the specified `INDEX`. The index refers to the index
  number shown in the displayed contact list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the entry will be removed i.e adding
  of tags is not cumulative.
* You can remove all the entry's tags by typing t/ without specifying any tags
  after it.
* After editing a journal entry, the list displayed in the `Journal` tab will be
  reset to show all existing contacts, because the previous filtering operation
  may not still have the same effects on the edited contact.
* `DATE_AND_TIME` must be in the format: "YYYY-MM-DD HH:MM"
* `TAG` must be alphanumeric, without spaces.

</div>

#### Viewing a journal entry: `view in/j`

Opens up a journal entry to show further details.

Aliases: `view`, `v`

Format: `view in/j index/INDEX`

* `INDEX` refers to the index number of the contact shown in the list.
* `SCOPE` must be `c` (refers to contact) or `j` (refers to journal entry).

Examples:

* `view in/j index/4` Views the 4th journal entry in the journal.
* `view in/j index/8` Views the 8th journal entry in the journal.

#### Finding journal entries: `find in/j`

Finds a list of contacts or journal entries that satisfy the requirements
on particular fields given by the user.

Aliases: `find`, `f`

Format: `find in/j [n/TITLE_KEYWORDS] [at/DATE_AND_TIME]
         [with/CONTACT_NAME_KEYWORDS]…​ [d/DESCRIPTION_KEYWORDS] [t/TAG]…​`

<div markdown="block" class="alert alert-primary">

:bulb: **Tips:**<br>

* `DATE_AND_TIME` must be in the format: "YYYY-MM-DD HH:MM"
* `TAG` must be alphanumeric, without spaces.
* Note that for `TAG`, IntelliJournal searches for the exact same tag instead of
  tags containing any part of the searched terms.

</div>

Examples:

* `find in/j n/Meeting` Finds all journal entries with a title containing
  `Meeting`.
* `find in/j n/Meeting d/plan for 2021 with/Alice t/Meeting at/2020-10-10 15:00`
  <br>Finds all journal entries that have a title containing `Meeting`, a
  description containing `plan for 2021`, with a contact whose name contains
  `Alice`, tagged `Meeting`, and happening on Oct 10, 2020, 3pm.

#### Checking schedule: `check`

Finds a list of journal entries for a date given by the user.

Aliases: `check`, `ck`

Format: `check [DATE]`

Example 1:

1. Type `check 2020-09-20` into the command box and press enter.

    ![check example](images/UGExamples/checkExample1.png)

1. This finds a list of journal entries on the date 2020-09-20.

    ![check example 2](images/UGExamples/checkExample2.png)

Example 2:

1. Type `check` into the command box and press enter. This finds a list of
   journal entries on the current date.

    ![check example 3](images/UGExamples/checkExample3.png)

1. This finds a list of journal entries on the current date
   (2020-11-04 in the example).

    ![check example 4](images/UGExamples/checkExample4.png)

#### Deleting a journal entry: `deletej`

Deletes a journal entry from the journal.

Aliases: `deletej`, `delj`

Format: `deletej INDEX`

* `INDEX` refers to the index number of the journal shown in the list.

Example:

1. Type `deletej 5` into the command box and press enter.

    ![deletej example 1](images/UGExamples/deletejExample1.png)

1. This deletes the 5th journal entry in the journal.

    ![deletej example 1](images/UGExamples/deletejExample1.png)

#### Clearing the journal: `clearj`

Clears all journal entries in the journal.

Aliases: `clearj`, `cj`

Format: `clearj`

Example:

1. Type `clearj` into the command box and press enter.

    ![clearj example 1](images/UGExamples/clearjExample1.png)

1. This removes all journal entries in the journal.

    ![clearj example 2](images/UGExamples/clearjExample2.png)

### Miscellaneous

#### Viewing help menu: `help`

Gives the explanation and format of commands in the system and does not change
the current displaying tab.

Aliases: `help`, `h`

Format: `help [of/COMMAND]`

* The `of/COMMAND` argument supplied will indicate which command explanation to
  show.
* If no `of/COMMAND` argument is supplied, you will see a new window containing
the link to our User Guide.

Example 1:

1. Type `help of/addc` in the command box and press enter.

    ![Ui](images/UGExamples/helpExample1.png)

1. The information pane should give you information on how to use the `addc`
   command.

   ![help example 2](images/UGExamples/helpExample2.png)

Example 2:

1. Type `help` in the command box and press enter.

    ![help example 3](images/UGExamples/helpExample3.png)

1. There should be a pop-up with a link to this user guide.

    ![help example 4](images/UGExamples/helpExample4.png)

#### Switching the display tab: `switch`

Switches the current displaying tab to the other tab.

Aliases: `switch`, `swt`

Format: `switch`

#### Adding aliases for existing commands: `alias`

Adds your preferred shortcut for existing commands.

Aliases: `alias`, `al`

Format: `alias TARGET ALIAS`

* `TARGET`: Existing command or alias in the IntelliJournal.
* `ALIAS`: The personalized shortcut you want to add into the system.

<div markdown="block" class="alert alert-info">

**:information_source: Note:**<br>

* We have provided some default shortcuts which can be found in
  [Command Summary](#command-summary), you can use `alias` command to add your
  personalized command alias.
* You can add a shortcut according to existing shortcut. eg. If you have added
  `st` as a shortcut for `switch`, you can use `alias st si`. You will add `si`
  as a valid alias for switch as well.
* **Warning**: IntelliJournal will not be able to have a single alias for two
  different commands.
  <br>Eg. If `st` has been added to the system, it will confuse IntelliJournal
  if you ask it to add `st` as a valid alias for `check` or other command.

</div>

Example:

* `alias switch st` ---  This will add `st` as a valid shortcut for command
  `switch`.
* `alias addj j` --- This will add `j` as a valid shortcut for command `addj`

#### Deleting custom aliases: `deletea`

Removes the custom alias from IntelliJournal.

Aliases: `deletea`, `dela`

Format: `deletea ALIAS`

Example:

1. Assuming you have added `st` as an alias for `switch`, type `deletea sw` into
   the command box and press enter.

    ![deletea example](images/UGExamples/deleteaExample1.png)

1. This removes `st` from IntelliJournal.

    ![deletea example 2](images/UGExamples/deleteaExample2.png)

1. If you try to use `st` again, IntelliJournal will tell you that the command
   is not recognised.

    ![deletea example 3](images/UGExamples/deleteaExample3.png)

#### Changing the main color theme: `changetheme`

You can use this command to choose between two main color scheme of IntelliJournal.
One is dark theme and the other is bright theme mainly.

Aliases: `changetheme`, `ctheme`

Format: `changetheme`

#### Exiting the program: `exit`

Exits the program.

Aliases: `exit`, `quit`, `q`

Format: `exit`

#### Saving the data

IntelliJournal data is saved in the hard disk automatically after any command
that changes the data. There is no need to save manually.

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file
it creates with the file that contains the data of your previous
IntelliJournal home folder.

---

## Command Summary

### Contacts

<table>
  <thead>
        <tr>
            <th>Command</th>
            <th>Format</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="2"><b>Adding a contact</b></td>
            <td>
                <code>
                    addcontact n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]
                    [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td>
                <code>
                    addc n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td rowspan="2"><b>Listing all contacts</b></td>
            <td><code>listc</code></td>
        </tr>
        <tr>
            <td><code>lc</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Editing a contact</b></td>
            <td>
                <code>
                    editc INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]
                    [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td>
                <code>
                    edc INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]
                    [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td rowspan="2"><b>Viewing a contact</b></td>
            <td><code>view in/c index/INDEX</code></td>
        </tr>
        <tr>
            <td><code>v in/c index/INDEX</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Finding contacts</b></td>
            <td>
                <code>
                    find in/c [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS]
                    [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td>
                <code>
                    f in/c [n/NAME_KEYWORDS] [p/PHONE_KEYWORDS]
                    [e/EMAIL_KEYWORDS] [a/ADDRESS_KEYWORDS] [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td rowspan="2"><b>Deleting a contact</b></td>
            <td><code>deletec INDEX</code></td>
        </tr>
        <tr>
            <td><code>delc INDEX</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Clearing the address book</b></td>
            <td><code>clearc</code></td>
        </tr>
        <tr>
            <td><code>cc</code></td>
        </tr>
    </tbody>
</table>

### Journal Entries

<table>
  <thead>
        <tr>
            <th>Command</th>
            <th>Format</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="3"><b>Adding a journal entry</b></td>
            <td>
                <code>
                    addjournal n/TITLE [at/DATE_AND_TIME] [d/DESCRIPTION]
                    [with/CONTACT_NAME]…​ [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td>
                <code>
                    addj n/TITLE [at/DATE_AND_TIME] [d/DESCRIPTION]
                    [with/CONTACT_NAME]…​ [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td>
                <code>
                    adde n/TITLE [at/DATE_AND_TIME] [d/DESCRIPTION]
                    [with/CONTACT_NAME]…​ [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td rowspan="2"><b>Listing all journal entries</b></td>
            <td><code>listj</code></td>
        </tr>
        <tr>
            <td><code>lj</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Editing a journal entry</b></td>
            <td>
                <code>
                    editj INDEX [n/TITLE] [at/DATE_AND_TIME] [d/DESCRIPTION]
                    [with/CONTACT_NAME]…​ [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td>
                <code>
                    edj INDEX [n/TITLE] [at/DATE_AND_TIME] [d/DESCRIPTION]
                    [with/CONTACT_NAME]…​ [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td rowspan="2"><b>Viewing a journal entry</b></td>
            <td><code>view in/j index/INDEX</code></td>
        </tr>
        <tr>
            <td><code>v in/j index/INDEX</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Finding journal entries</b></td>
            <td>
                <code>
                    find in/j [n/TITLE_KEYWORDS] [at/DATE_AND_TIME]
                    [with/CONTACT_NAME_KEYWORDS]…​ [d/DESCRIPTION_KEYWORDS]
                    [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td>
                <code>
                    f in/j [n/TITLE_KEYWORDS] [at/DATE_AND_TIME]
                    [with/CONTACT_NAME_KEYWORDS]…​ [d/DESCRIPTION_KEYWORDS]
                    [t/TAG]…​
                </code>
            </td>
        </tr>
        <tr>
            <td rowspan="2"><b>Checking schedule</b></td>
            <td><code>check [DATE]</code></td>
        </tr>
        <tr>
            <td><code>ck [DATE]</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Deleting a journal entry</b></td>
            <td><code>deletej INDEX</code></td>
        </tr>
        <tr>
            <td><code>delj INDEX</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Clearing the journal</b></td>
            <td><code>clearj</code></td>
        </tr>
        <tr>
            <td><code>cj</code></td>
        </tr>
    </tbody>
</table>

### Miscellaneous

<table>
  <thead>
        <tr>
            <th>Command</th>
            <th>Format</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="2"><b>Viewing help menu</b></td>
            <td><code>help [COMMAND]</code></td>
        </tr>
        <tr>
            <td><code>h [COMMAND]</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Switching the display tab</b></td>
            <td><code>switch</code></td>
        </tr>
        <tr>
            <td><code>swt</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Adding aliases for existing commands</b></td>
            <td><code>alias TARGET ALIAS</code></td>
        </tr>
        <tr>
            <td><code>al TARGET ALIAS</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Deleting custom aliases</b></td>
            <td><code>deletea ALIAS</code></td>
        </tr>
        <tr>
            <td><code>dela ALIAS</code></td>
        </tr>
        <tr>
            <td rowspan="2"><b>Changing the color theme</b></td>
            <td><code>changetheme</code></td>
        </tr>
        <tr>
            <td><code>ctheme</code></td>
        </tr>
        <tr>
            <td rowspan="3"><b>Exiting the program</b></td>
            <td><code>exit</code></td>
        </tr>
        <tr>
            <td><code>quit</code></td>
        </tr>
        <tr>
            <td><code>q</code></td>
        </tr>
    </tbody>
</table>

