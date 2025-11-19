import React from "react";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";

const Sidebar = () => {
    return (
        <div className="d-flex flex-column bg-dark text-white vh-100 p-3 shadow" style={{width: "260px"}}>
            <div className="d-flex align-items-center mb-4">
                <i className="fa-solid fa-dumbbell fs-3 me-2 text-primary"></i>
                <h4 className="m-0 fw-bold">RoutineSystem</h4>
            </div>

            <ul className="nav nav-pills flex-column mb-auto">
                <li className="nav-item mb-2">
                    <a href="/routines" className="nav-link text-white d-flex align-items-center rounded px-3 py-2 sidebar-link">
                        <i className="bi bi-house fs-5 me-2"></i><span>Rutinas</span>
                    </a>
                </li>

                <li className="nav-item">
                    <a href="/exercises" className="nav-link text-white d-flex align-items-center rounded px-3 py-2 sidebar-link">
                        <i className="bi bi-list-task fs-5 me-2"></i><span>Ejercicios</span>
                    </a>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;
