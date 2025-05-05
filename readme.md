# studentportal

Task Definition: Student portal

Objective:

The goal of this assessment is to build a Spring Boot-based Student portal that efficiently manages student data, result and report-generation with a secure and structured architecture.

1️⃣ Student Registration & Authentication

✅ Unique Student Identification
	•	Assign a GTU-style enrollment number (get).
	•	Ensure email & enrollment numbers are unique in the database.

✅ Authentication API (JWT-based)
	•	Secure login using email or enrollment number.
	•	Implement refresh token mechanism for session management.

✅ Forgot Password (Two-Step Process)
	•	Step 1: Generate token-based reset link and send via email.
	•	Step 2: On clicking the link, the user can reset password securely.

✅ Change Password & Profile Update API
	•	Allow students to update their details & passwords securely.

✅ Check-In API (Optional Attendance System Integration)
	•	If used, students check in daily to track attendance via QR Code / Geolocation API.


2️⃣ Result & Merit System

✅ View Past 8 Semester Results
	•	Fetch all semester records from the database.
	•	Optimize query for fast retrieval.

✅ Semester-wise Merit Ranking System
	•	Calculate merit list dynamically for each semester.
	•	Display student’s rank alongside class toppers.

✅ Rank Calculation (Points-Based)
	•	Allocate points based on performance criteria:
	•	90-100% → 5⭐
	•	75-89% → 4⭐
	•	Pass (below 75%) → 3⭐
	•	Fail → 0⭐

✅ Cumulative Performance Report
	•	Display CGPA trend across semesters.
	•	Provide course difficulty analytics (e.g., if multiple students fail a subject).



3️⃣ Monthly Report Generation (Scheduled Task)

✅ Automated Performance & Attendance Reports
	•	A scheduler runs at month-end to calculate:
	•	Student attendance summary.
	•	Performance rank generation using the point system.

✅ Attendance-Based Ranking System
	•	Attendance contributes to cumulative points:
	•	Daily attendance → 1⭐ per attended lecture.

✅ Email Report Delivery
	•	(Optional) Auto-send student progress reports via email. Notify students at risk (low grades, low attendance).

✅ Admin Dashboard for Insights
	•	(Optional) Display overall performance trends, top students, at-risk students.



🔹 Security Enhancements

1️⃣ Role-Based Access Control (RBAC)

We’ll define roles in the system:
	•	ADMIN → Can manage students, results, reports, attendance.
	•	STUDENT → Can view results, merit list, attendance, and update profile.


 NOTES:
Additionally, you can add custom exception handling
Can use DB of your choice
Should have MVC structure
Use the same response format in all the HTTP responses with generic entities.



