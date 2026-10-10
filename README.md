# Multi-College Management SaaS

## Product Vision, Requirements, Architecture, and Delivery Plan

**Document status:** Initial product specification\
**Product type:** Multi-tenant college management Software-as-a-Service
(SaaS)\
**Initial target:** 1--10 colleges in the first two years\
**Capacity planning target:** Up to 5,000 students per college, subject
to load testing\
**Primary clients:** Android application and web administration portal\
**Initial funding constraint:** Minimize costs until a college commits
to a pilot

------------------------------------------------------------------------

## 1. Executive Summary

The product is a cloud-based college management platform for multiple
colleges. It will provide role-specific experiences for Platform
Administrators, College Administrators, Heads of Department (HODs),
Teachers, and Students.

The product will use **one shared Android application and one shared
backend** for all colleges. Each college will have configurable
branding---such as college name, logo, colors, and enabled
features---without requiring a separate application codebase or database
for each college.

A web portal will support college administration and platform
operations. The system will be designed as a **multi-tenant SaaS**:
colleges share the application infrastructure while their data and
access remain strictly isolated.

The system should be production-minded from the start but inexpensive to
develop. Development can begin locally with synthetic data. Paid
production services should be introduced when a college is ready to
pilot and the agreed service, security, availability, and recovery
requirements are known.

## 2. Product Goals

1.  Digitize common college administration and academic workflows.
2.  Give each user a dashboard and permissions appropriate to their
    role.
3.  Provide secure access to attendance, notices, leave, timetables,
    marks, and documents.
4.  Support multiple colleges from one maintainable product.
5.  Allow college branding and selected features to be configured
    without code changes.
6.  Support a planning target of up to 5,000 students per college,
    verified through realistic performance tests.
7.  Minimize initial operating costs while keeping a clear path to
    production.
8.  Provide an administrative web portal for workflows that are
    cumbersome on mobile.
9.  Maintain reliable audit trails, backup procedures, monitoring, and
    incident response before real student data is onboarded.

## 3. Product Principles

-   **One shared product:** One primary Android app, one web portal, and
    one backend codebase.
-   **Tenant isolation first:** A user from one college must never be
    able to access another college's data without explicit, authorized
    membership.
-   **Server-side authorization:** Hiding a screen or button in the
    client is not security.
-   **Configuration over customization:** Prefer college-specific
    settings and feature flags over separate code branches.
-   **Modular monolith initially:** Keep one deployable backend with
    clearly separated business modules. Extract services only when real
    needs justify the added operational complexity.
-   **Production-upgradable:** Keep environment configuration, database
    migrations, automated tests, and deployment repeatable.
-   **Privacy and least privilege:** Collect and expose only the data
    required for a defined workflow.
-   **Cost-aware delivery:** Use local development and eligible free
    tiers for demos; do not depend on temporary credits for production
    continuity.

## 4. Users and Roles

The product has four primary college roles plus a separate
platform-level role.

### 4.1 Platform Administrator

Responsible for operating the SaaS platform itself.

Potential permissions: - Create, activate, suspend, and configure
college tenants. - Manage subscription plans and tenant status. - View
platform health and operational audit events. - Configure product-wide
settings and feature availability. - Assist with tenant onboarding and
support under audited, restricted procedures.

Platform administrators should not automatically receive unrestricted
access to academic records. Any support access to tenant data should be
explicitly authorized, limited, and audited.

### 4.2 College Administrator

Responsible for administration within one college.

Potential permissions: - Manage the college's profile, logo, branding,
and settings. - Manage users, departments, programs, academic years,
classes, sections, subjects, and assignments. - Configure timetable
rules and document templates. - Publish college-wide notices. - Manage
authorized document and fee workflows. - View permitted college-wide
reports. - Assign roles within the college, subject to platform policy.

### 4.3 HOD

Responsible for a department or assigned academic scope.

Potential permissions: - View departmental students, teachers, classes,
and subjects. - Review attendance summaries. - Approve or review leave
requests according to college policy. - Review marks and academic
reports within their scope. - Manage departmental notices and timetable
inputs if authorized. - Review relevant audit events.

### 4.4 Teacher

Responsible for assigned classes, subjects, and teaching activities.

Potential permissions: - View assigned timetable and class lists. -
Create or submit attendance for authorized sessions. - View and manage
marks for assigned subjects and assessments. - Apply for leave and view
the status of their requests. - Publish notices only where authorized. -
View relevant student information needed for teaching.

### 4.5 Student

Responsible for their own academic and administrative activities.

Potential permissions: - View their profile and academic information. -
View their own attendance, timetable, marks, notices, and
notifications. - Submit leave requests where student leave is
supported. - Request bonafide certificates, transfer certificates, and
other permitted documents. - View or download their own authorized
documents and fee receipts. - Update allowed profile fields.

### 4.6 Role and Permission Rules

-   A role is scoped to a college membership; a global user record must
    not have only one fixed role.
-   A user may belong to more than one college and may have different
    roles at each.
-   Resource-level checks must verify the user's actual assignment or
    ownership, not just their role.
-   Approval workflows must be configurable to match each college's
    policy.
-   All permission decisions must be enforced by the backend and tested.

## 5. Functional Requirements

### 5.1 Authentication and Account Management

-   Login and logout.
-   Secure account recovery.
-   Account activation, suspension, and revocation.
-   User membership in one or more colleges.
-   College selection or switching for users with multiple memberships.
-   Role-aware navigation and dashboards.
-   Secure session/token lifecycle.
-   Registration should be controlled: public self-registration must not
    automatically grant access to a college. A college invitation,
    verified admission record, or authorized administrator should
    establish membership.

### 5.2 College Onboarding and Branding

Each tenant should support configuration such as: - College name and
unique college code. - Logo and optional banner. - Primary and secondary
brand colors. - Contact information and address. - Enabled feature
modules. - Academic calendar and time zone. - Document templates and
numbering conventions. - Notification preferences. - College status and
subscription plan.

The client may display branding selected for the active college, but
branding data is not an authorization credential.

### 5.3 Student and Staff Management

-   Create, update, deactivate, and search student and staff records.
-   Assign students to programs, academic years, classes, and sections.
-   Assign teachers to subjects and classes.
-   Support paginated lists and filtered searches.
-   Support validated bulk imports from CSV or spreadsheet templates.
-   Report invalid or duplicate rows rather than silently dropping data.
-   Preserve historical enrollment and academic-year records where
    required.
-   Restrict each user to authorized records.

### 5.4 Attendance Management

-   Create attendance sessions associated with class, subject, teacher,
    and date/time.
-   Allow authorized teachers to mark attendance.
-   Validate enrollment and teacher assignment on the backend.
-   Submit attendance in batches where appropriate.
-   Prevent duplicate attendance records for the same student and
    session.
-   Support authorized corrections with reasons and audit history.
-   Provide student attendance views and class/department summaries.
-   Export reports only for authorized users.
-   Test concurrent submissions and retry behavior.

### 5.5 Notice Board

-   Create, edit, publish, schedule, archive, and target notices
    according to permissions.
-   Support college-wide, department, class, or other configured
    audiences.
-   Allow attachments stored in private object storage.
-   Record the publisher, publication time, audience, and status.
-   Ensure a user only receives notices targeted to their authorized
    audience.
-   Maintain a database record as the authoritative notice; push
    notifications are delivery alerts, not the only copy of the notice.

### 5.6 Leave Management

-   Submit leave requests with dates, type, reason, and optional
    supporting files.
-   Track states such as Submitted, Under Review, Approved, Rejected, or
    Cancelled, according to policy.
-   Configure the approval chain by role and scope.
-   Notify applicants and authorized approvers of relevant status
    changes.
-   Prevent unauthorized decisions and duplicate or conflicting actions.
-   Preserve decision history, timestamps, and reasons.

### 5.7 Documents and Certificates

Supported document categories may include: - Bonafide certificate. -
Transfer certificate (TC). - Admission receipt. - Fee receipt. - Other
college-configured certificates or records.

Requirements: - Students submit requests for documents they are eligible
to request. - Authorized staff review and approve requests. - Templates,
required fields, approvers, and numbering rules are configurable. -
Issued documents have stable identifiers and issuance records. - Store
document metadata in PostgreSQL and file content in private object
storage. - Authorize access before generating a short-lived file
download URL. - Record issuance, reissue, cancellation, and relevant
administrative actions. - Do not allow a client to create an official
receipt by submitting an arbitrary amount. Receipts must be based on
verified fee/payment records.

### 5.8 Timetable Management

-   Create timetable entries for academic year, class/section, subject,
    teacher, room, and time.
-   Validate overlaps and conflicts where rules are known.
-   Allow authorized administrators or HODs to publish updates.
-   Display timetables filtered by the active user's role and
    assignments.
-   Notify affected users about relevant timetable changes.
-   Preserve enough history to investigate changes.

### 5.9 Marks and Assessments

-   Create assessments and define maximum marks and grading rules.
-   Assign assessments to subjects, classes, and academic periods.
-   Allow authorized teachers to enter and submit marks.
-   Validate ranges, student enrollment, and teacher assignment.
-   Support review/finalization and controlled correction workflows.
-   Display students only their own authorized marks.
-   Audit changes to marks, including actor, timestamp, and reason.
-   Support authorized reports and exports.

### 5.10 Dynamic Notifications

-   Maintain in-app notification records in the database.
-   Use Firebase Cloud Messaging (FCM) for push delivery.
-   Target notifications to eligible users based on tenant, role, class,
    or relevant assignment.
-   Send push messages from the trusted backend, never directly from the
    Android client using server credentials.
-   Track processing attempts and relevant delivery outcomes.
-   Retry transient failures with bounded backoff.
-   Remove or deactivate invalid device tokens.
-   Use a transactional outbox or equivalent reliable event mechanism
    for important notifications.
-   Avoid including sensitive student information in push payloads;
    retrieve authorized details through the API.

### 5.11 Profile, About Us, and Contact Us

-   Role-appropriate profile page.
-   Editable profile fields with validation and permission checks.
-   College-specific About Us and contact information.
-   Support contact form or contact details.
-   Privacy policy, terms, and support information.
-   Account deactivation or deletion request process where applicable.

### 5.12 Subscription and Platform Operations

The architecture should allow future SaaS billing without making it a
blocker for the initial demo.

Potential capabilities: - Subscription plans and feature entitlements. -
Tenant activation, suspension, renewal, and cancellation. - Usage limits
and plan status. - Billing records and payment-gateway integration if
required. - Platform support and tenant onboarding workflows.

Payment-gateway integration should be added only after the commercial
model and required payment flows are agreed.

## 6. Recommended Technology Stack

### Android

-   Android Studio.
-   Kotlin.
-   Jetpack Compose and Material 3.
-   MVVM and Repository pattern.
-   Kotlin Coroutines and Flow.
-   Navigation Compose.
-   Retrofit/OkHttp for REST APIs.
-   Room for selected offline structured data.
-   DataStore for non-sensitive preferences.
-   Secure token storage and FCM integration.

### Backend

-   Java.
-   Spring Boot on a currently supported release.
-   Spring Security.
-   REST API versioning, initially `/api/v1`.
-   OpenAPI/Swagger documentation.
-   Spring Data JPA/Hibernate for common persistence operations.
-   SQL/projections for performance-sensitive reporting where
    appropriate.
-   Flyway for database migrations.
-   JUnit 5 and integration tests.
-   Spring Boot Actuator with protected management endpoints.

### Database

-   PostgreSQL.
-   Foreign keys, unique constraints, transactions, and indexes.
-   Tenant-aware data access.
-   Consider PostgreSQL Row Level Security as defense in depth, with
    correctly configured roles and connection context.
-   Automated backups and tested restoration for production.

### Web Portal

-   React.
-   TypeScript.
-   Responsive and accessible administrative UI.
-   Shared API contracts with the Android app.
-   Role-aware navigation backed by server-side authorization.

### Files and Notifications

-   Private S3-compatible object storage; Google Cloud Storage is the
    selected option if using the proposed Google Cloud deployment.
-   Firebase Cloud Messaging for push notifications.
-   Optional transactional email provider for account recovery and
    official email communication.

### DevOps and Operations

-   Git and GitHub.
-   Docker.
-   GitHub Actions for CI/CD.
-   Managed cloud hosting.
-   Cloud logging and monitoring.
-   Secret manager.
-   Dependency and container vulnerability scanning.
-   Separate development, staging, and production environments.

## 7. System Architecture

The initial architecture is a **modular monolith**: one deployable
Spring Boot backend divided into well-defined business modules.

### High-Level Flow

1.  Android app or React portal sends an HTTPS request.
2.  Backend validates authentication.
3.  Backend resolves the user's active college membership.
4.  Backend checks role and resource-level permissions.
5.  Business service validates the requested operation.
6.  PostgreSQL stores or retrieves authorized records.
7.  Private object storage provides document content when authorized.
8.  Notification events are processed asynchronously and sent through
    FCM.
9.  Logs and audit events support investigation and operations.

### Backend Modules

-   `identity`
-   `tenancy`
-   `college-config`
-   `academics`
-   `students`
-   `attendance`
-   `timetable`
-   `leave`
-   `notices`
-   `examinations`
-   `documents`
-   `fees`
-   `notifications`
-   `subscriptions`
-   `audit`
-   `common`

Each module should own its API controllers, request/response DTOs,
business services, repositories, validation, and tests as appropriate.
Avoid exposing persistence entities directly through the public API.

Do not begin with microservices, Kubernetes, Kafka, or multiple
databases unless measured needs justify them.

## 8. Multi-Tenant Architecture

### Recommended Initial Model

Use one PostgreSQL database with shared tables for all colleges. Every
tenant-owned record must be associated with its college, directly or
through a rigorously enforced parent relationship.

### Core Tenant Rules

-   Every college-owned table has a non-null `college_id`, where
    appropriate.
-   The backend derives authorized tenant context from authenticated
    membership.
-   Never trust a `college_id` supplied by a client as proof of access.
-   Use composite foreign keys and unique constraints to prevent
    cross-tenant relationships.
-   Apply tenant filters consistently to reads, updates, deletes,
    searches, reports, and exports.
-   Use database Row Level Security as additional protection where
    practical.
-   Ensure database connection pooling cannot leak tenant context
    between requests.
-   Test cross-tenant access automatically, including document downloads
    and bulk exports.
-   Keep platform-level administration separate from college
    administration.
-   Store branding and feature configuration by tenant, not by code
    branch.

### One Shared App vs. White-Label Apps

The default offering is one shared Android app and one Play Store
listing. Users identify or select their college, authenticate, and see
its branding and authorized data.

If a future college pays for a white-label app, generate a separately
branded build from the same codebase using build configurations/product
flavors. A separate app does not inherently require a separate backend
or database.

## 9. Database Design Outline

The final schema should be reviewed through an ER diagram before
implementation. A starting entity set is:

### Tenant and Identity

-   `colleges`
-   `campuses`
-   `users`
-   `college_memberships`
-   `roles`
-   `permissions`
-   `membership_roles`

### Academic Structure

-   `academic_years`
-   `departments`
-   `programs`
-   `classes`
-   `sections`
-   `subjects`
-   `teaching_assignments`
-   `students`
-   `student_enrollments`

### Academic Operations

-   `timetable_entries`
-   `attendance_sessions`
-   `attendance_records`
-   `assessments`
-   `marks`
-   `leave_requests`
-   `notices`
-   `notice_targets`

### Documents and Fees

-   `document_requests`
-   `documents`
-   `document_templates`
-   `fee_invoices`
-   `fee_receipts`

### Platform Operations

-   `subscriptions`
-   `subscription_plans`
-   `device_tokens`
-   `notifications`
-   `outbox_events`
-   `audit_events`
-   `idempotency_keys`

### Database Design Rules

-   Unique student numbers should be scoped to the relevant college and
    policy.
-   Attendance should have a uniqueness constraint for the college,
    session, and student.
-   Monetary values should use an appropriate fixed-precision numeric
    type.
-   Timestamps should use consistent time-zone-aware handling.
-   Frequently filtered columns should be indexed based on actual query
    patterns.
-   Academic history should not be overwritten if it needs to remain
    auditable.
-   Use transactions for important multi-record operations.
-   Use pagination rather than loading thousands of records into memory
    at once.
-   Apply schema changes through reviewed Flyway migrations.

This is a starting outline, not a final schema. The exact columns,
cardinalities, deletion rules, and constraints must be established from
detailed workflows.

## 10. Security and Privacy

Security is a launch requirement, especially because the platform
processes student identities, academic records, and financial documents.

### Authentication

-   Use a suitable OpenID Connect identity provider with Spring
    Security, or implement authentication carefully using established
    security libraries.
-   Use secure account recovery, session/token expiry, revocation, and
    account suspension.
-   Avoid public self-registration that grants college access without
    verification.

### Authorization

-   Enforce tenant membership and resource permissions on every
    protected operation.
-   Do not rely on UI visibility or client-supplied role values.
-   Restrict teachers to assigned classes/subjects and students to their
    own permitted records.
-   Restrict document access to authorized users and approved workflows.
-   Audit privileged platform support access.

### Data Protection

-   HTTPS/TLS in transit and managed encryption at rest.
-   Secrets stored outside source control and client applications.
-   Private database and storage access.
-   Short-lived signed download URLs for files.
-   Validate uploaded file types and sizes; consider malware scanning.
-   Do not log passwords, tokens, or unnecessary sensitive information.
-   Define retention, export, deletion, and incident procedures.
-   Review applicable Indian data-protection and contractual
    requirements with qualified advisers before real data is onboarded.

### Audit

Sensitive actions such as marks edits, attendance corrections, leave
decisions, document issuance, role changes, and tenant administration
should record the actor, tenant, target, timestamp, action, and relevant
change details. Protect audit records from unauthorized modification.

### Security Testing

Test: - Cross-tenant reads and writes. - Privilege escalation. -
IDOR/resource-ID manipulation. - Unauthorized exports and file
downloads. - Token revocation and account suspension. - Input validation
and rate limits. - Database policies and connection-context handling. -
Backup restoration and incident recovery.

## 11. Hosting and Cost Strategy

### Development and Demo

Keep costs near zero: - Run Spring Boot and PostgreSQL locally where
practical. - Use synthetic student data. - Use GitHub and eligible
Student Developer Pack benefits. - Use free-tier services only within
their published quotas and terms. - Avoid purchasing a hosting
subscription before it is needed. - Set billing alerts before enabling
cloud services that can charge money.

### Proposed Production Setup

If the selected provider remains Google Cloud: - **Cloud Run:** Run the
Dockerized Spring Boot API. - **Cloud SQL for PostgreSQL:** Managed
production database. - **Cloud Storage:** Private document storage. -
**Firebase Cloud Messaging:** Push notifications. - **Cloud Secret
Manager:** Application secrets. - **Cloud Logging/Monitoring:**
Operational logs, metrics, and alerts. - **Managed static hosting:**
React administration portal. - **GitHub Actions:** Automated build,
test, and controlled deployment.

These are separate services with separate cost drivers. The selected
region, database configuration, backups, traffic, storage, and instance
settings affect the actual bill. Verify pricing before launch.

### Production Alternatives

A self-managed VPS can have a lower apparent monthly price but requires
the team to maintain operating-system updates, PostgreSQL, security,
backups, monitoring, and recovery. Choose it only if the team can
operate it responsibly.

Do not assume temporary student credits will fund a commercial service
indefinitely. Check eligibility and commercial-use terms.

## 12. Production Deployment and Environments

Maintain separate environments: - **Development:** Local or isolated
resources; synthetic data. - **Staging:** Production-like configuration
for integration, migration, and acceptance tests. - **Production:**
Restricted access, real data, monitored services, backups, and
controlled releases.

Deployment pipeline: 1. Developer creates a branch and pull request. 2.
CI runs formatting/linting where configured, unit tests, integration
tests, and security/dependency checks. 3. A reviewed change is merged.
4. A versioned Docker image is built. 5. The image is deployed to
staging. 6. Database migrations and acceptance tests are checked. 7. An
authorized release is promoted to production. 8. Health, error, latency,
and database metrics are monitored. 9. Rollback procedure is available.

Production secrets and credentials must never be committed to Git. Use
least-privilege service identities.

## 13. Backups, Reliability, and Recovery

Before the first college launch: - Configure automated database
backups. - Enable point-in-time recovery where appropriate and available
in the chosen plan. - Define recovery point objective (RPO) and recovery
time objective (RTO). - Test restoration into a separate environment. -
Define retention and deletion policies for documents. - Consider object
versioning or other recovery controls for important files. - Document a
response process for data corruption, service outage, leaked
credentials, and unauthorized access. - Test application rollback
independently from database rollback.

A backup is not proven until restoration has been tested.

## 14. Performance and Scalability

The planning target is up to 5,000 students per college. This is not a
performance guarantee; the workload must be measured.

Initial design practices: - Paginated student and staff queries. -
Indexed tenant, class, student, subject, and date access paths. - Batch
attendance submissions. - Transactions and uniqueness constraints for
concurrent operations. - Connection pooling sized to the database limits
and application concurrency. - Asynchronous notification processing for
work that does not need to block the user. - Rate limits on
authentication and sensitive endpoints. - Efficient reporting queries
and bounded exports. - Load tests using realistic data volumes and
expected concurrent users.

Start with one modular backend and a managed database. Add Redis, a
dedicated queue, read replicas, or separate services only when observed
workload and reliability needs justify them.

Potential initial service objectives should be agreed before launch. For
example, an API availability target and a p95 latency target can be
proposed, then validated with load tests. Exclude large reports and file
transfers from ordinary request latency objectives where appropriate.

## 15. Implementation Roadmap

The schedule below is an initial planning estimate for a small team, not
a guarantee.

### Phase 1 --- Requirements and Foundation

-   Confirm role and permission matrix.
-   Define user journeys and approval rules.
-   Draft ER diagram and tenant model.
-   Create repositories, CI, backend skeleton, and database migrations.
-   Implement authentication, memberships, and tenant authorization.
-   Add cross-tenant security tests.

**Exit condition:** Core identity and tenant security tests pass.

### Phase 2 --- Academic Structure

-   College onboarding and branding.
-   Departments, programs, academic years, classes, sections, subjects.
-   Student and teacher management.
-   Teacher assignments.
-   Bulk import validation.
-   Timetable management.

**Exit condition:** A college can configure its academic structure
without manual database edits.

### Phase 3 --- Core Workflows

-   Attendance.
-   Leave requests and approvals.
-   Notices and in-app notification records.
-   Marks and assessments.
-   Android dashboards for students and teachers.
-   Web portal workflows for administrators and HODs.

**Exit condition:** Main workflows operate end to end with role checks
and audit history.

### Phase 4 --- Documents and Operations

-   Bonafide and TC requests.
-   Fee and admission receipt records.
-   Private file storage.
-   FCM delivery and retry processing.
-   Audit views, operational logging, and subscription foundations.

**Exit condition:** Document access is controlled and notification
failures can be investigated/retried.

### Phase 5 --- Pilot Readiness

-   Production cloud configuration.
-   Security review and cross-tenant tests.
-   Load and concurrency tests.
-   Backup and restore tests.
-   Data import rehearsal.
-   Support procedures and college training.
-   Privacy and contractual review.

**Exit condition:** No unresolved critical security issues; agreed
performance and recovery criteria are met.

### Phase 6 --- Pilot and Expansion

-   Launch with one college and a controlled user group.
-   Reconcile imported data.
-   Monitor issues and user feedback.
-   Fix defects before expanding access.
-   Document repeatable onboarding.
-   Add further colleges through configuration, not separate code
    branches.

## 16. Demo-to-Production Transition

When a college expresses interest:

1.  Conduct a discovery meeting and confirm the requested scope.
2.  Agree on a pilot plan, acceptance criteria, responsibilities, costs,
    and launch date.
3.  Review the college's data, import formats, and existing workflows.
4.  Create a production environment separate from the demo.
5.  Harden authentication, authorization, tenant isolation, file access,
    and audit logging.
6.  Configure managed hosting, database, private storage, secrets,
    monitoring, and backups.
7.  Run security, concurrency, performance, and recovery tests.
8.  Perform a trial import using protected test data.
9.  Obtain approval of data reconciliation.
10. Import production data using a documented process.
11. Pilot with selected administrators and teachers.
12. Expand only after agreed acceptance criteria are met.

Do not place real student data into an unreviewed demo or free-tier
setup merely because a college is interested.

## 17. Risks and Mitigations

  -----------------------------------------------------------------------
  Risk                                Mitigation
  ----------------------------------- -----------------------------------
  Cross-college data exposure         Tenant-aware authorization,
                                      database constraints, RLS where
                                      practical, automated security tests

  Costs exceed budget                 Billing alerts, usage monitoring,
                                      quotas, cost review, avoid
                                      unnecessary infrastructure

  Free-tier service pauses or limits  Treat free tiers as demo-only; plan
                                      migration to production services

  Data loss                           Automated backups, retention,
                                      tested restoration, defined RPO/RTO

  Duplicate attendance or repeated    Database constraints, transactions,
  operations                          idempotency

  Wrong document access               Private storage, server-side
                                      authorization, short-lived URLs

  Excessive customization per college Configuration and feature flags;
                                      charge for exceptional custom work

  Unmaintainable backend              Modular monolith, code reviews,
                                      tests, clear module boundaries

  Unreliable notifications            Database-backed notification
                                      records, outbox/queue processing,
                                      retries

  Poor data import quality            Validation, dry runs,
                                      reconciliation, college approval

  Dependency or credential compromise Secret manager, least privilege,
                                      dependency scanning, rotation,
                                      incident response
  -----------------------------------------------------------------------

## 18. Suggested Repository Structure

``` text
college-saas/
├── android-app/
├── admin-web/
├── backend/
│   └── src/main/java/com/company/college/
│       ├── identity/
│       ├── tenancy/
│       ├── collegeconfig/
│       ├── academics/
│       ├── students/
│       ├── attendance/
│       ├── timetable/
│       ├── leave/
│       ├── notices/
│       ├── examinations/
│       ├── documents/
│       ├── fees/
│       ├── notifications/
│       ├── subscriptions/
│       ├── audit/
│       └── common/
├── database/
│   └── migrations/
├── infrastructure/
├── docs/
└── .github/
    └── workflows/
```

The exact package structure can evolve, but modules should have clear
ownership and dependency boundaries.

## 19. Decisions to Finalize Before Implementation

The following items require explicit product decisions: - Product name
and domain. - Whether student self-registration is allowed and how
identity is verified. - Exact approval chain for each leave and document
type. - Academic calendar, grading, and attendance rules. - Whether
parents/guardians will be supported in a later release. - Required
languages and accessibility requirements. - Which fee records are
informational versus integrated with a payment provider. - Required
historical data and retention periods. - Expected concurrent usage, peak
periods, RPO, and RTO. - Hosting region and contractual/data-protection
requirements. - Subscription tiers and white-label pricing. - Whether
dedicated infrastructure will be offered to certain customers.

These decisions should be recorded and versioned rather than embedded as
undocumented assumptions in code.

## 20. Definition of Done for the First Production College

The product is ready for its first production college only when: - The
college's agreed scope and acceptance criteria are documented. -
Authentication and role/resource permissions are verified. -
Cross-tenant access tests pass. - Production infrastructure and secrets
are configured. - Database migrations are repeatable. - Backups and
restoration are verified. - Private document access is tested. -
Attendance, leave, notices, marks, and included document workflows pass
end-to-end tests. - Data import is reconciled and approved. -
Performance is tested against the expected workload. - Monitoring,
alerts, support, and incident-response procedures are ready. - Privacy
and contractual responsibilities are addressed. - A rollback and
recovery plan exists. - The college approves the pilot results.

------------------------------------------------------------------------

## Final Recommendation

Build **one multi-tenant SaaS product** using Kotlin + Jetpack Compose
for Android, React + TypeScript for the web portal, Java + Spring Boot
for the backend, and PostgreSQL for structured data. Use private object
storage for documents and FCM for push notifications.

Start with a modular monolith and a shared tenant-aware schema. Develop
locally and use synthetic data to keep costs low. Introduce paid managed
hosting only when a college is ready for a pilot. Before production,
prioritize tenant isolation, access control, backups, recovery testing,
audit trails, privacy, and repeatable deployment.

The aim is not to build a large distributed system immediately. It is to
build a secure, maintainable product that can onboard the first college
reliably and add more colleges without duplicating the codebase or
compromising data isolation.
