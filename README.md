# Eurder

Timing
To properly implement all the stories, in a test-first way with a proper design and architecture, an experienced developer will need around 2-3 days, it might be more, it might be less. Therefore, we've created two categories of stories: Must-Have's and Nice-To-Have's. Start with the Must-Have's, then do the Nice-To-Have's.

=====
Must-Have stories:

Story 0 (your project setup + other technical requirements)
Story 1
Story 2
Story 3
Story 7
Story 8
Nice-To-Have stories:

Story 4
Story 5
Story 6
Story 9
Story 10

=====
Technical requirements for JAVA
Create a new GitHub repository
Create a REST(ful) Web API (with JSON as the message / payload format)
Use Spring Boot
Use Maven as the Build & Dependency Management project tool.
Perform logging
Certainly log all interactions with the application that can be defined as "errors"
E.g.: unauthorized access, illegal arguments, exceptions in general,...
Provide, through OpenAPI and Swagger(UI) an online manual / documentation for your Web API.
If you have already seen JPA: use JPA (Hibernate or EclipseLink) in combination with a PostgreSQL or Oracle Database to store and access the data.
Setup a proper test configuration, which runs the integration tests against an in-memory database (e.g. H2)
Make it a separate technical story.
Correctly setup and handle the transactions.
Write your DDL (create tables,...) in a separate .sql file, which you also put under version control.
Use Jenkins to set up a Continuous Integration (CI) pipeline.
Additionally, but optional, deploy to Heroku!
Think about Security: authentication and authorization. It is not a priority, but if you have the time, implement it properly.
Until then, you can neglect the fact that certain endpoints should only be usable by - for example - an administrator.

=====
Commit messages :
[IMP] for improvements
[FIX] for bug fixes
[REF] for refactoring
[ADD] for adding new resources
[REM] for removing of resources
[MOV] for moving files (Do not change content of moved file, otherwise Git will loose track, and the history will be lost !), or simply moving code from a file to another one.
[MERGE] for merge commits (only for forward/back-port)