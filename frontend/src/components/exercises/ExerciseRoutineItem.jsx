import React, { useState } from 'react';
import Swal from "sweetalert2";

const ExerciseRoutineItem = ({ exercise, availableExercises, routineId, isEditing: initialIsEditing, onSave, onCancel, onDelete }) => {
    const [isEditing, setIsEditing] = useState(initialIsEditing);
    const [exerciseId, setExerciseId] = useState(exercise?.exerciseId || '');
    const [sets, setSets] = useState(exercise?.sets || '');
    const [reps, setReps] = useState(exercise?.reps || '');
    const [weight, setWeight] = useState(exercise?.weight || '');

    const handleSave = () => {
        if (!exerciseId) {
            Swal.fire({
                icon: "warning",
                title: "Seleccioná un ejercicio",
                text: "Debes elegir un ejercicio antes de guardar.",
            });
            return;
        }
        if (sets <= 0 || reps <= 0) {
            Swal.fire({
                icon: "error",
                title: "Valores inválidos",
                text: "Las series y repeticiones deben ser mayores a 0.",
            });
            return;
        }
            onSave({
                routineId,
                exerciseId: parseInt(exerciseId),
                sets: parseInt(sets),
                reps: parseInt(reps),
                weight: weight ? parseFloat(weight) : 0
            });
            setIsEditing(false);
    };

    const handleCancel = () => {
        setExerciseId(exercise?.exerciseId || '');
        setSets(exercise?.sets || '');
        setReps(exercise?.reps || '');
        setWeight(exercise?.weight || '');
        setIsEditing(false);
        onCancel();
    };

    if (isEditing) {
        return (
            <form className="exercise-item editing" onSubmit={(e) => {e.preventDefault();}}>
                <div className="exercise-form">
                    <div className="form-row">
                        <div className="form-group">
                            <label>Ejercicio</label>
                            <select
                                className="exercise-select"
                                value={exerciseId}
                                onChange={(e) => setExerciseId(e.target.value)}
                            >
                                <option value="" disabled>Seleccionar</option>
                                {availableExercises.map(ex => (
                                    <option key={ex.id} value={ex.id}>
                                        {ex.name} ({ex.muscle})
                                    </option>
                                ))}
                            </select>
                        </div>

                    <div className="form-group">
                            <label>Series</label>
                            <input
                                type="number"
                                className="exercise-input"
                                value={sets}
                                onChange={(e) => setSets(e.target.value)}
                            />
                        </div>

                        <div className="form-group">
                            <label>Reps</label>
                            <input
                                type="number"
                                className="exercise-input"
                                value={reps}
                                onChange={(e) => setReps(e.target.value)}
                            />
                        </div>

                        <div className="form-group">
                            <label>Peso (kg)</label>
                            <input
                                type="number"
                                className="exercise-input"
                                value={weight}
                                onChange={(e) => setWeight(e.target.value)}
                                min="0"
                                step="0.5"
                            />
                        </div>
                    </div>
                </div>

                <div className="exercise-actions">
                    <button className="btn-icon btn-save" onClick={handleSave} title="Guardar">
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
                    <span className="exercise-title">{exercise.exerciseName}</span>
                    <span className="exercise-muscle">{exercise.muscle}</span>
                </div>
                <div className="exercise-details">
                    <span>{exercise.sets} series</span>
                    <span>{exercise.reps} reps</span>
                    {exercise.weight > 0 && (
                        <>
                            <span>{exercise.weight} kg</span>
                        </>
                    )}
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

export default ExerciseRoutineItem;
