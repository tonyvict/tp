---
layout: page
title: Developer Guide
---
# Developer Guide

* Table of Contents
  {:toc}

---

## **1. Product Scope (Target User & Value Proposition)**

### **Target User Profile**

Private tutors and small teaching teams who manage many students without the infrastructure of a tuition agency and need a fast, keyboard-driven desktop tool to keep contacts, lessons, attendance, grades, and notes organized.

### **Value Proposition**

ClassRosterPro reduces tutors' admin load by consolidating contacts, tagging/filtering, lesson scheduling, attendance tracking, grade recording, and quick search into one command-first desktop app—so time goes to teaching, not record-keeping.

---

## **2. User Stories (with MoSCoW Priorities)**

| Priority | As a …   | I want to …                                                                 | So that I can …                                                                                 |
| -------- | -------- | --------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| ***      | New user | Access the help page                                                        | Familiarise myself with the commands available in the program                                   |
| ***      | Tutor    | Add a new student contact                                                   | Easily contact them for class updates                                                           |
| ***      | Tutor    | Delete a student contact                                                    | Keep my contacts updated                                                                        |
| ***      | Tutor    | Edit a student's contact details                                            | Update their information when it changes                                                        |
| ***      | Tutor    | Search for a student contact by name                                        | Quickly find their contact                                                                      |
| ***      | Tutor    | Tag and filter students by their attributes (e.g. name, subject, age, etc.) | Categorise students with similar attributes and look them up more easily                        |
| ***      | Tutor    | Delete specific attributes from students                                    | Remove outdated or incorrect information                                                        |
| ***      | Tutor    | Schedule lessons for students                                               | Keep track of upcoming classes and avoid scheduling conflicts                                   |
| ***      | Tutor    | Unschedule lessons                                                          | Remove cancelled or rescheduled lessons from my roster                                          |
| ***      | Tutor    | Record student attendance for specific lessons                              | Track which students attended which classes                                                     |
| ***      | Tutor    | Unmark attendance                                                           | Correct attendance records if marked incorrectly                                                |
| ***      | Tutor    | Record grades for students by subject and assessment                        | Maintain a detailed grade book for each student                                                 |
| ***      | Tutor    | Open/close student contact cards                                            | View detailed or summary information as needed                                                  |
| **       | Tutor    | List all students (reset filters)                                           | Always return to the full view                                                                  |
| **       | Tutor    | Tag with multi-values (e.g., subject=math,science)                          | Group students flexibly by multiple criteria                                                    |
| **       | Tutor    | Use quick search by name, email, or phone                                   | Instantly find students during lessons                                                          |
| **       | Tutor    | See search counts (e.g., "2 students found")                                | Get quick feedback on filter results                                                            |

### **Could-Have**

* Undo/redo last action to recover from mistakes (in backlog; aligns with productivity)
* Export/import roster to a file for backup
* Bulk updates for multiple students

### **Considered (Not for current release)**

* Calendar sync with external providers
* Dashboard analytics (future epics)
* Automated reminders for upcoming lessons

---

## **3. Representative Use Cases**

### **UC-1: Add and tag a student**

**Goal:** Add a new student and assign attributes.
**Scope:** Roster management
**Primary actor:** Tutor
**Preconditions:** None

**Main Success Scenario (MSS):**

1. Tutor enters `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`.
2. System validates all fields and adds the student.
3. Tutor enters `tag 1 attr/subject=math attr/age=16`.
4. System updates the student and confirms tags.

**Extensions:**

* 1a. Invalid email/phone format → show error message and reject.
* 1b. Duplicate contact → show error "This person already exists".
* 3a. Invalid index → show "Invalid person index".
* 3b. Invalid attribute format → show "Incorrect format" and do nothing.

---

### **UC-2: Schedule a lesson for a student**

**Preconditions:** Student exists in the roster.

**MSS:**

1. Tutor types `schedule 1 start/14:00 end/15:00 date/2025-09-20 sub/science`.
2. System validates index, time format (HH:mm), date format (YYYY-MM-DD), and time validity.
3. System checks for overlapping lessons on the same date.
4. System saves and confirms the lesson.

**Extensions:**

* 2a. Invalid time format → error "Invalid start/end time format. Use HH:mm".
* 2b. Invalid time values (e.g., 24:01) → error "Invalid time. Hours must be 00-23 and minutes must be 00-59".
* 2c. Invalid date format → error "Invalid date format. Use YYYY-MM-DD".
* 2d. Invalid date values (e.g., 2025-11-31) → error "Invalid date. Ensure the day is valid for the given month and year".
* 2e. End time ≤ start time → error "End time must be after start time".
* 3a. Lesson overlaps with existing lesson → reject with "This lesson overlaps with an existing lesson".
* 3b. Lesson duplicates an existing one → reject with "This lesson already exists".

---

### **UC-3: Record attendance for a specific lesson**

**Preconditions:** Student exists with at least one scheduled lesson.

**MSS:**

1. Tutor filters/finds the student if needed.
2. Tutor enters `mark 1 lesson/1`.
3. System validates both indices and marks the lesson as attended.
4. System updates attendance count and confirms.

**Extensions:**

* 2a. Student index invalid → error "Invalid person index".
* 2b. Lesson index invalid → error "Invalid lesson index".
* 2c. Lesson already marked → update confirmation message.

---

### **UC-4: Record grades for a student**

**Preconditions:** Student exists in the roster.

**MSS:**

1. Tutor types `grade 2 sub/MATH/WA1/89 sub/SCIENCE/Quiz1/95`.
2. System validates index and grade format.
3. System saves grades and confirms.

**Extensions:**

* 1a. Invalid index → error "Invalid person index".
* 2a. Invalid grade format → error "Incorrect format".
* 2b. Subject/assessment/score empty → error message.
* 2c. Duplicate subject-assessment in command → last occurrence wins.
* 2d. Subject-assessment already exists for student → overwrites with new score.

---

### **UC-5: Unschedule a lesson**

**Preconditions:** Student exists with at least one scheduled lesson.

**MSS:**

1. Tutor lists/filters students.
2. Tutor enters `unschedule 1 lesson/1`.
3. System validates both indices.
4. System removes the lesson and confirms.

**Extensions:**

* 2a. Student index out of bounds → error "Invalid person index".
* 2b. Lesson index out of bounds → error "Invalid lesson index".
* 3a. Student has no lessons → error "The selected person has no lessons scheduled".

---

### **UC-6: Delete attributes from a student**

**Preconditions:** Student exists with at least one attribute.

**MSS:**

1. Tutor types `deltag 1 attr/subject`.
2. System validates index and attribute key.
3. System removes the specified attribute and confirms.

**Extensions:**

* 1a. Invalid index → error "Invalid person index".
* 2a. Attribute doesn't exist → no error, command succeeds.
* 2b. Multiple attributes specified → all valid attributes removed.

---

### **UC-7: Filter students by attributes**

**MSS:**

1. Tutor types `filter attr/subject=math,science attr/age=16`.
2. System applies filters (AND logic between different attributes, OR within same attribute).
3. System displays filtered list with count.

**Extensions:**

* 1a. No students match → display "0 students listed".
* 2a. Invalid age value (non-integer) → error message.
* 2b. No attr/ prefix provided → error "Incorrect format".

---

### **UC-8: Search a student**

**MSS:**

1. Tutor types in the search box (e.g., "alex" or "9876" or "john12").
2. System searches name, phone, and email fields in real-time.
3. System displays matching results.

**Extensions:**

* 1a. No matches found → display empty list.
* 2a. Search cleared → return to full list.

---

### **UC-9: Delete a student**

**MSS:**

1. Tutor lists/filters students.
2. Tutor enters `delete 3`.
3. System validates index and removes the contact.
4. System confirms deletion.

**Extensions:**

* 2a. Index out of bounds → error "Invalid person index".
* 2b. Index not a positive integer → error message.

---

## **4. Non-Functional Requirements (NFRs)**

| Category               | Requirement                                                                                                      |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------- |
| **Portability**        | Runs on mainstream OS (Windows/macOS/Linux) with Java 17+.                                                       |
| **Performance**        | Responsive command execution (≤150ms parse/dispatch). Handles ~1,000 students smoothly.                          |
| **Usability**          | Keyboard-first with GUI support; all actions via commands with clear error messages. Help window lists all commands and examples. Quick search provides instant feedback. |
| **Reliability**        | Atomic saves with JSON backup. Detects corrupted storage and continues running with empty data file.             |
| **Data Validation**    | Strict validation for time (00:00-23:59), date (valid calendar dates), email format, and phone numbers.          |
| **Maintainability**    | Command pattern structure. Each new feature = new Command + parser + model update. Unit tests mandatory.         |
| **Security & Privacy** | Data stored locally in JSON format. No auto-sync. Contact details remain private.                                |
| **Scalability**        | Support for multiple lessons per student, multiple attributes, multiple grades per subject.                      |

---

## **5. Glossary**

| Term                | Definition                                                                                  |
| ------------------- | ------------------------------------------------------------------------------------------- |
| **Command**         | A typed instruction (e.g., `add`, `delete`, `find`, `schedule`) processed by the logic layer. |
| **Index**           | 1-based position of a student in the current displayed list (after `list`/`find`/`filter`). |
| **Lesson Index**    | 1-based position of a lesson in a student's lesson list. |
| **Tag / Attribute** | Key–value metadata attached to a student (e.g., `subject=math`, `age=16`). Can have multiple comma-separated values. |
| **Remark**          | Short free-text note attached to a student for remembering important details. |
| **Attendance**      | Mark/unmark record indicating if a student attended a specific lesson. |
| **Lesson**          | A scheduled time block (date, start time, end time, subject) tied to a student. |
| **Grade**           | A subject-assessment-score triplet recorded for a student (e.g., MATH/WA1/89). |
| **List**            | Default roster view showing all students; also the command that resets filters. |
| **Filter**          | Command to display only students matching specified attribute criteria. |
| **Quick Search**    | Real-time search feature that finds students by name, email, or phone number. |
| **Help**            | Window listing all supported commands with examples and grouped by category. |
| **Overlap**         | When two lessons for the same student share any time period on the same date. |
| **Duplicate Lesson**| An exact match of student, date, start time, end time, and subject with an existing lesson. |

---

## **6. Validation Rules**

### **Time Format**
- Pattern: `HH:mm` (24-hour format)
- Valid hours: 00-23
- Valid minutes: 00-59
- Examples: `09:30`, `14:00`, `23:59`
- Invalid: `24:00`, `14:60`, `9:30` (missing leading zero)

### **Date Format**
- Pattern: `YYYY-MM-DD` (ISO 8601)
- Must be a valid calendar date
- Examples: `2025-09-20`, `2024-02-29` (leap year)
- Invalid: `2025-11-31` (November has 30 days), `2025-02-30`, `2023-02-29` (not leap year)

### **Email Format**
- Must contain `@` symbol
- Basic validation for common patterns

### **Phone Number**
- Must be numeric
- Reasonable length constraints

### **Attribute Keys and Values**
- Case-insensitive matching
- Multiple values separated by commas
- Age values must be valid integers

### **Index Values**
- Must be positive integers (1, 2, 3, ...)
- Must be within bounds of current displayed list

---

## **7. Architecture Overview**

ClassRosterPro follows a layered architecture adapted from AddressBook-Level3, ensuring high cohesion and low coupling across modules.  
Each major layer has a distinct responsibility:
- **UI**: Handles user interactions through JavaFX components and FXML layouts (e.g., `MainWindow`, `QuickSearchBox`).
- **Logic**: Parses user commands and executes them, returning `CommandResult` objects to the UI.
- **Model**: Manages in-memory data such as `Person`, `Lesson`, `Grade`, and `Tag`.
- **Storage**: Reads and writes persistent data to local JSON files.
- **Commons**: Contains shared utilities such as `Messages`, `LogsCenter`, and custom exceptions.

This modular structure supports easy feature addition (e.g., new commands) with minimal changes to existing code.
