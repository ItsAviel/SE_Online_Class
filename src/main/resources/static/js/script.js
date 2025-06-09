// ------------ LOGIN AND REGISTRATION ------------

async function loginUser() {
    const id = Number(document.getElementById('login_id').value.trim());
    const password = Number(document.getElementById('login_password').value.trim());
    const msg = document.getElementById('login_msg');
    msg.textContent = '';

    const JSON_TO_SEND = {
        id: Number(id),
        password: Number(password)
    };

    try {
        const response = await fetch('/profile', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(JSON_TO_SEND)
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            msg.textContent = errorResponse.message;
            throw new Error(errorResponse.message);
        }

        const profile = await response.json();
        localStorage.setItem('profile', JSON.stringify(profile));
        window.location.href = profile.role === 'student' ? './student.html' : './teacher.html';

    } catch (err) {
        msg.textContent = err.message;
    }
}

function registerUser() {
    const name = document.getElementById('register_name').value;
    const id = document.getElementById('register_id').value.trim();
    const password = document.getElementById('register_password').value.trim();
    const email = document.getElementById('register_email').value.trim();
    const role = document.getElementById('register_role').value;
    const msg = document.getElementById('register_msg');
    msg.textContent = '';

    if (!name || !id || !email || !password) {
        msg.textContent = 'אנא מלא את כל השדות';
        return;
    }

    const JSON_TO_SEND = {
        id: Number(id),
        password: Number(password),
        name: name,
        email: email
    };

    let url = role === 'teacher' ? '/teacher' : '/student';
    let errorMessage = `Error signing up ${role}`;

    fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(JSON_TO_SEND)
    })
        .then(res => res.text().then(text => {
            if (res.ok) {
                msg.textContent = text;
            } else {
                throw new Error(`${errorMessage}: ${text}`);
            }
        }))
        .catch(err => {
            msg.textContent = err.message;
        });
}



// ------------ LOAD PAGE ------------

document.addEventListener('DOMContentLoaded', () => {
    const profile = JSON.parse(localStorage.getItem('profile'));
    if (!profile) return;


    if (profile.role === 'student' && document.getElementById("student_lessons_list")) {
        initStudent(profile);
    } else if (profile.role === 'teacher' && document.getElementById("teacher_all_lessons")) {
        initTeacher(profile);
    }
});




// ------------ STUDENT PAGE ------------


function initStudent() {
    setUpProfile();
    getAllLessons();



}


// function setUpProfile() {
//     const profile = JSON.parse(localStorage.getItem('profile'));
//     if (!profile) return;

//     const profileDiv = document.getElementById("user_profile");
//     profileDiv.innerHTML = `
//         <strong>שם:</strong> ${profile.username} |
//         <strong>ת.ז:</strong> ${profile.user_id} |
//         <strong>אימייל:</strong> ${profile.email} |
//         <strong>ביוגרפיה:</strong> ${profile.bio} 
//     `;
// }


// function setUpProfile() {
//     const profile = JSON.parse(localStorage.getItem('profile'));
//     if (!profile) return;

//     const profileDiv = document.getElementById("user_profile");

//     profileDiv.innerHTML = `

//         <div><strong>שם:</strong> ${profile.username}</div>
//         <div><strong>ת.ז:</strong> ${profile.user_id}</div>
//         <div>
//             <strong>אימייל:</strong>
//             <input type="email" id="emailInput" value="${profile.email || ''}">
//         </div>
//         <div>
//             <strong>ביוגרפיה:</strong><br>
//             <textarea id="bioTextarea" rows="3">${profile.bio || ''}</textarea>
//         </div>
//         <button id="saveProfileBtn">שמור</button>
//     `;

//     document.getElementById("saveProfileBtn").addEventListener("click", () => {
//     const profile = JSON.parse(localStorage.getItem("profile"));
//     const updatedEmail = document.getElementById("profile_email").value;
//     const updatedBio = document.getElementById("profile_bio").value;

//     fetch("/profile/updateProfile", {
//         method: "PUT",
//         headers: { "Content-Type": "application/json" },
//         body: JSON.stringify({
//             user_id: profile.user_id,
//             email: updatedEmail,
//             bio: updatedBio
//         })
//     })
//     .then(res => res.text())
//     .then(msg => {
//         alert(msg);
//         profile.email = updatedEmail;
//         profile.bio = updatedBio;
//         localStorage.setItem("profile", JSON.stringify(profile));
//     })
//     .catch(err => console.error("Error updating profile:", err));
// });

// }

function setUpProfile() {
    const profile = JSON.parse(localStorage.getItem('profile'));
    if (!profile) return;

    // מיקום אלמנטים
    document.getElementById("profileName").textContent = profile.username || '';
    document.getElementById("profileId").textContent = profile.user_id || '';
    document.getElementById("profileEmailText").textContent = profile.email || '';
    document.getElementById("profileEmailInput").value = profile.email || '';
    document.getElementById("profileBioText").textContent = profile.bio || '';
    document.getElementById("profileBioInput").value = profile.bio || '';
   

    const profile_msg = document.getElementById('profile_msg');
    profile_msg.textContent = '';

    let isEditing = false;

    document.getElementById("editSaveBtn").addEventListener("click", () => {
        const emailText = document.getElementById("profileEmailText");
        const bioText = document.getElementById("profileBioText");
        const emailInput = document.getElementById("profileEmailInput");
        const bioInput = document.getElementById("profileBioInput");

        if (!isEditing) {
            // עבור למצב עריכה
            emailText.style.display = "none";
            bioText.style.display = "none";
            emailInput.style.display = "inline-block";
            bioInput.style.display = "inline-block";
            document.getElementById("editSaveBtn").textContent = "שמור";
            isEditing = true;
        } else {
            // שמירה
            const updatedEmail = emailInput.value;
            const updatedBio = bioInput.value;

            fetch("/profile/updateProfile", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    user_id: profile.user_id,
                    email: updatedEmail,
                    bio: updatedBio
                })
            })
            .then(res => res.text())
            .then(msg => {
                profile_msg.textContent = msg;

                // עדכון מקומי ו־localStorage
                profile.email = updatedEmail;
                profile.bio = updatedBio;
                localStorage.setItem("profile", JSON.stringify(profile));

                // עדכון תצוגה
                emailText.textContent = updatedEmail;
                bioText.textContent = updatedBio;
                emailText.style.display = "inline";
                bioText.style.display = "inline";
                emailInput.style.display = "none";
                bioInput.style.display = "none";
                document.getElementById("editSaveBtn").textContent = "ערוך פרופיל";
                isEditing = false;
            })
            .catch(err => {
                
                profile_msg.textContent = err.message;
                console.error(err);
            });
        }
    });
}






function getAllLessons() {
    fetch("/lesson")
        .then(response => response.json())
        .then(allLessons => {
            const tbody = document.getElementById("student_lessons_list");
            tbody.innerHTML = '';

            const profile = JSON.parse(localStorage.getItem('profile'));
            const userId = profile?.user_id;

            allLessons.forEach(lesson => {
                const row = createLessonRow(lesson, userId);
                tbody.appendChild(row);
            });
        })
        .catch(err => {
            console.error("Error fetching lessons: ", err);
        });
}


// function createLessonRow(lesson, userId) {
//     const tr = document.createElement("tr");

//     const tdId = document.createElement("td");
//     tdId.textContent = lesson.id;
//     tr.appendChild(tdId);

//     const tdTitle = document.createElement("td");
//     tdTitle.textContent = lesson.title;
//     tr.appendChild(tdTitle);

//     const tdStudents = document.createElement("td");
//     tdStudents.innerHTML = lesson.students
//         .map(s => `Name: ${s.name}, ID: ${s.id}, Email: ${s.email}`)
//         .join('<br>');
//     tr.appendChild(tdStudents);

//     const tdAction = createActionCell(lesson, userId);
//     tr.appendChild(tdAction);

//     return tr;
// }
function createLessonRow(lesson, userId) {
    const tr = document.createElement("tr");

    const tdId = document.createElement("td");
    tdId.textContent = lesson.id;
    tr.appendChild(tdId);

    const tdTitle = document.createElement("td");
    tdTitle.textContent = lesson.title;
    tr.appendChild(tdTitle);

    const tdTeacher = document.createElement("td");
    tdTeacher.textContent = lesson.teacherName || "";
    tr.appendChild(tdTeacher);

    const tdStudents = document.createElement("td");
    tdStudents.innerHTML = lesson.students
        // .map(s => `Name: ${s.name}, ID: ${s.id}, Email: ${s.email}`)
        .map(s => ` שם תלמיד: ${s.name}, ת.ז: ${s.id}, אימייל: ${s.email}`)
        .join('<br>');
    tr.appendChild(tdStudents);

    const tdAction = createActionCell(lesson, userId);
    tr.appendChild(tdAction);

    return tr;
}


function createActionCell(lesson, userId) {
    const td = document.createElement("td");

    const isRegistered = lesson.students.some(s => s.id === userId);
    const msg = document.getElementById('student_msg');

    if (isRegistered) {
        const leaveBtn = document.createElement("button");
        leaveBtn.textContent = "בטל רישום";
        leaveBtn.classList.add("leave-btn");

        leaveBtn.addEventListener("click", async () => {
            try {
                const res = await fetch('/lesson/delete', {
                    method: 'DELETE',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ lessonId: lesson.id, userId: userId })
                });
                msg.textContent = await res.text();
                getAllLessons();
            } catch (err) {
                msg.textContent = "Error leaving lesson: " + err.message;
                console.error(err);
            }
        });
        td.appendChild(leaveBtn);
    } else {
        const joinBtn = document.createElement("button");
        joinBtn.textContent = "הירשם";
        joinBtn.classList.add("join-btn");

        joinBtn.addEventListener("click", async () => {
            try {
                const res = await fetch('/lesson/join', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ lessonId: lesson.id, userId: userId })
                });
                msg.textContent = await res.text();
                getAllLessons();
            } catch (err) {
                msg.textContent = "Error joining lesson: " + err.message;
                console.error(err);
            }
        });
        td.appendChild(joinBtn);
    }

    return td;
}




// ------------ TEACHER PAGE ------------


function initTeacher(profile) {
    const teacherId = profile.user_id;
    setUpProfile();

    // טען את כל השיעורים
    loadAllLessons();

    // טען את השיעורים של המרצה הנוכחי
    loadTeacherLessons(teacherId);

    // כפתור הוספת שיעור
    document.getElementById('add_btn')?.addEventListener('click', async () => {
        const lessonTitle = document.getElementById('new_lesson_name').value;
        const msg = document.getElementById('teacher_msg');

        const res = await fetch('/lesson', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title: lessonTitle, teacherId: teacherId })
        });

        msg.textContent = await res.text();

        // רענן את שתי הרשימות
        loadAllLessons();
        loadTeacherLessons(teacherId);
    });
}

// async function loadTeacherLessons(teacherId) {
//     try {
//         const res = await fetch(`/lesson/${teacherId}`);
//         const lessons = await res.json();
//         const tbody = document.getElementById("teacher_current_lessons_list");
//         if (!tbody) return;

//         tbody.innerHTML = '';

//         lessons.forEach(lesson => {
//             const tr = document.createElement("tr");

//             const tdId = document.createElement("td");
//             tdId.textContent = lesson.id;
//             tr.appendChild(tdId);

//             const tdTitle = document.createElement("td");
//             tdTitle.textContent = lesson.title;
//             tr.appendChild(tdTitle);

//             const tdStudents = document.createElement("td");
//             tdStudents.innerHTML = lesson.students
//                 .map(s => `Name: ${s.name}, ID: ${s.id}, Email: ${s.email}`)
//                 .join('<br>');
//             tr.appendChild(tdStudents);

//             tbody.appendChild(tr);
//         });
//     } catch (err) {
//         console.error("Error loading teacher lessons:", err);
//     }
// }

async function loadTeacherLessons(teacherId) {
    try {
        const res = await fetch(`/lesson/${teacherId}`);
        const lessons = await res.json();
        const tbody = document.getElementById("teacher_current_lessons_list");
        if (!tbody) return;

        tbody.innerHTML = '';

        lessons.forEach(lesson => {
            const tr = document.createElement("tr");

            const tdId = document.createElement("td");
            tdId.textContent = lesson.id;
            tr.appendChild(tdId);

            const tdTitle = document.createElement("td");
            tdTitle.textContent = lesson.title;
            tr.appendChild(tdTitle);

            const tdTeacher = document.createElement("td");
            tdTeacher.textContent = lesson.teacherName || "";
            tr.appendChild(tdTeacher);

            const tdStudents = document.createElement("td");
            tdStudents.innerHTML = lesson.students
                //.map(s => `Name: ${s.name}, ID: ${s.id}, Email: ${s.email}`)
                .map(s => ` שם תלמיד: ${s.name}, ת.ז: ${s.id}, אימייל: ${s.email}`)
                .join('<br>');
            tr.appendChild(tdStudents);

            // ⬅️ כפתור מחיקה
            const tdDelete = document.createElement("td");
            const deleteBtn = document.createElement("button");
            deleteBtn.textContent = "מחק";
            deleteBtn.classList.add("delete-btn");
            deleteBtn.addEventListener("click", async () => {
                if (confirm(`האם אתה בטוח שברצונך למחוק את השיעור "${lesson.title}"?`)) {
                    const delRes = await fetch(`/lesson/deleteLesson/${lesson.id}`, {
                        method: "DELETE"
                    });

                    const msg = await delRes.text();
                    document.getElementById("teacher_msg").textContent = msg;

                    // רענון טבלאות
                    loadAllLessons();
                    loadTeacherLessons(teacherId);
                }
            });

            tdDelete.appendChild(deleteBtn);
            tr.appendChild(tdDelete);

            tbody.appendChild(tr);
        });
    } catch (err) {
        console.error("Error loading teacher lessons:", err);
    }
}


async function loadAllLessons() {
    try {
        const res = await fetch('/lesson');
        const lessons = await res.json();
        const tbody = document.getElementById("teacher_all_lessons");
        if (!tbody) return;

        tbody.innerHTML = '';

        lessons.forEach(lesson => {
            const tr = document.createElement("tr");

            const tdId = document.createElement("td");
            tdId.textContent = lesson.id;
            tr.appendChild(tdId);

            const tdTitle = document.createElement("td");
            tdTitle.textContent = lesson.title;
            tr.appendChild(tdTitle);

            const tdTeacher = document.createElement("td");
            tdTeacher.textContent = lesson.teacherName || "";
            tr.appendChild(tdTeacher);

            const tdStudents = document.createElement("td");
            tdStudents.innerHTML = lesson.students
                // .map(s => `Name: ${s.name}, ID: ${s.id}, Email: ${s.email}`)
                .map(s => ` שם תלמיד: ${s.name}, ת.ז: ${s.id}, אימייל: ${s.email}`)
                .join('<br>');
            tr.appendChild(tdStudents);

            tbody.appendChild(tr);
        });
    } catch (err) {
        console.error("Error loading all lessons:", err);
    }
}


