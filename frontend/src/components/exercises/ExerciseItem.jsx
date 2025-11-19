import React, { useState } from 'react';
import Swal from "sweetalert2";

const ExerciseItem = ({ exercise, onSave, onCancel, onDelete, isEditing: initialIsEditing, availableMuscles}) => {

    const [isEditing, setIsEditing] = useState(initialIsEditing);
    const [name, setName] = useState(exercise?.name || '');
    const [muscle, setMuscle] = useState(exercise?.muscle || '');


    const handleSave = () => {
        if (name.trim() && muscle.trim()) {
            onSave({
                name: name.trim(),
                muscle: muscle.trim()
            });
            setIsEditing(false);
        } else {
            Swal.fire({
                icon: "warning",
                title: "Campos incompletos",
                text: "El nombre y el músculo del ejercicio son requeridos.",
            });
        }
    };

    const handleCancel = () => {
        setName(exercise?.name || '');
        setMuscle(exercise?.muscle || '');
        setIsEditing(false);
        onCancel();
    };

    if (isEditing) {
        return (
            <form className="exercise-item editing" onSubmit={(e) => {e.preventDefault(); handleSave();}}>
                <div className="exercise-form">
                    <div className="form-row">
                        <div className="form-group">
                            <label>Nombre</label>
                            <input
                                type="text"
                                className="exercise-input"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            />
                        </div>

                        <div className="form-group">
                            <label>Músculo</label>
                            <select
                                className="exercise-select"
                                value={muscle}
                                onChange={(e) => setMuscle(e.target.value)}
                            >
                                <option value="">Seleccionar músculo</option>
                                {availableMuscles && availableMuscles.map((m) => (
                                    <option key={m.id || m.name || m} value={m.name || m}>
                                        {m.name || m}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                </div>

                <div className="exercise-actions">
                    <button className="btn-icon btn-save" type="submit" title="Guardar">
                        <i className="bi bi-check-lg"></i> Guardar
                    </button>
                    <button className="btn-icon btn-cancel" onClick={handleCancel} title="Cancelar">
                        <i className="bi bi-x-lg"></i> Cancelar
                    </button>
                </div>
            </form>
        );
    }

    return (
        <div className="exercise-item">
            <div className="exercise-info">
                <div className="exercise-name">
                    <span className="exercise-title">{exercise.name}</span>
                    <span className="exercise-muscle">{exercise.muscle}</span>
                </div>
            </div>

            <div className="exercise-actions">
                <button
                    className="btn-icon btn-edit"
                    onClick={() => setIsEditing(true)}
                    title="Editar"
                >
                    <i className="bi bi-pencil-fill bi-xs"></i>
                </button>
                <button
                    className="btn-icon btn-delete"
                    onClick={onDelete}
                    title="Eliminar"
                >
                    <i className="bi bi-trash3-fill"></i>
                </button>
            </div>
        </div>
    );
};

export default ExerciseItem;