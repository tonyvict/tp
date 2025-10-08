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

Private tutors and small teaching teams who manage many students without the infrastructure of a tuition agency and need a fast, keyboard-driven desktop tool to keep contacts, lessons, attendance, and notes organized.

### **Value Proposition**

ClassRosterPro reduces tutors’ admin load by consolidating contacts, tagging/filtering, lesson scheduling, attendance tracking, and quick search into one command-first desktop app—so time goes to teaching, not record-keeping.

---

## **2. User Stories (with MoSCoW Priorities)**

| Priority | As a …   | I want to …                                                                 | So that I can …                                                                                 |
| -------- | -------- | --------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| ***      | New user | Access the help page                                                        | Familiarise myself with the commands available in the program                                   |
| ***      | Tutor    | Add a new student contact                                                   | Easily contact them for class updates                                                           |
| ***      | Tutor    | Delete a student contact                                                    | Keep my contacts updated                                                                        |
| ***      | Tutor    | Search for a student contact by name                                        | Quickly find their contact                                                                      |
| ***      | Tutor    | Tag and filter students by their attributes (e.g. name, subject, age, etc.) | Categorise students with similar attributes and look them up more easily                        |
| ***      | Tutor    | Record student attendance                                                   | Track how many classes each student has attended                                                |
| ***      | Tutor    | Attach remarks to a student                                                 | Remember the student’s specific description (e.g. strengths/weaknesses, special considerations) |
| ***      | Tutor    | Schedule student lesson times                                               | Avoid lesson clashes or missed classes                                                          |
| **       | Tutor    | List all students (reset filters)                                           | Always return to the full view                                                                  |
| **       | Tutor    | Tag with multi-values (e.g., subject=math,science)                          | Groups are flexible                                                                             |
| **       | Tutor    | See search counts (e.g., “2 students found”)                                | I get quick feedback                                                                            |

### **Could-Have**

* Undo/redo last action to recover from mistakes (in backlog; aligns with productivity)
* Export/import roster to a file for backup.

### **Considered (Not for current release)**

* Calendar sync with external providers
* Bulk updates
* Dashboard analytics (future epics)

---

## **3. Representative Use Cases**

### **UC-1: Add a tag to a student (find first)**

**Goal:** Tag an existing student with attributes (e.g., subject, age).
**Scope:** Roster management
**Primary actor:** Tutor
**Preconditions:** Student exists; list is visible.

**Main Success Scenario (MSS):**

1. Tutor enters `find <keyword>` to narrow the list.
2. System filters and shows the matching students (shows count).
3. Tutor enters `tag <INDEX> tag/subject=math tag/age=16`.
4. System updates the student and confirms tags.

**Extensions:**

* 3a. Invalid key/value → show “Incorrect format” and do nothing.
* 3b. Index out of range → show error; resume at step 2.

---

### **UC-2: Schedule a lesson for a student**

**Preconditions:** Student exists.

**MSS:**

1. Tutor types `schedule name/Jordan start/14:00 end/15:00 date/2025-09-20 sub/science`.
2. System validates name (case-insensitive), time, and date formats.
3. System saves and confirms the lesson.

**Extensions:**

* 2a. End time ≤ start time → error “End time must be after start time”.
* 2b. Lesson duplicates an existing one → reject with “Lesson already scheduled”.

---

### **UC-3: Record attendance**

**MSS:**

1. Tutor filters/finds the student if needed.
2. Tutor enters `mark <INDEX>` (or `unmark <INDEX>`).
3. System updates the attendance and confirms.

**Extensions:**

* 2a. Index invalid → error message; no change.

---

### **UC-4: Add a remark to a student**

**MSS:**

1. Tutor types `addremark <INDEX> remark/<TEXT>`.
2. System validates index & remark length, then saves.

**Extensions:**

* 1a. Empty or >200 chars → error; ask to re-enter.

---

### **UC-5: Delete a student**

**MSS:**

1. Tutor lists/filters students.
2. Tutor enters `delete <INDEX>`.
3. System removes the contact and confirms.

**Extensions:**

* 2a. Index out of bounds/negative → error; do nothing.

---

## **4. Non-Functional Requirements (NFRs)**

| Category               | Requirement                                                                                                      |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------- |
| **Portability**        | Runs on mainstream OS (Windows/macOS/Linux) with Java 17+.                                                       |
| **Performance**        | Responsive command execution (≤150ms parse/dispatch). Handles ~1,000 students smoothly.                          |
| **Usability**          | Keyboard-first; all actions via commands with clear error messages. Help window lists all commands and examples. |
| **Reliability**        | Atomic saves with backup file. Detects corrupted storage and continues running.                                  |
| **Maintainability**    | Command pattern structure. Each new feature = new Command + parser + model update. Unit tests mandatory.         |
| **Security & Privacy** | Data stored locally by default. No auto-sync. Contact details remain private.                                    |

---

## **5. Glossary**

| Term                | Definition                                                                                  |
| ------------------- | ------------------------------------------------------------------------------------------- |
| **Command**         | A typed instruction (e.g., `add`, `delete`, `find`) processed by the logic layer.           |
| **Index**           | 1-based position of a student in the current displayed list (after `list`/`find`/`filter`). |
| **Tag / Attribute** | Key–value metadata attached to a student (e.g., `subject=math`, `age=16`).                  |
| **Remark**          | Short free-text note (max 200 chars) attached to a student.                                 |
| **Attendance**      | Mark/unmark record indicating if a student attended a session.                              |
| **Lesson**          | A scheduled time block (date, start, end, subject) tied to a student.                       |
| **List**            | Default roster view showing all students; also the command that resets filters.             |
| **Help**            | Window listing all supported commands with examples.                                        |
