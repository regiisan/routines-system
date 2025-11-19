import React from 'react';
import ExerciseRoutineItem from './ExerciseRoutineItem';

const ExerciseRoutineList = ({ selectedRoutine, routineExercises, availableExercises, isCreating, onCreateExercise, onUpdateExercise, onDeleteExercise, onStartCreating, onCancelCreating}) => {

    if (!selectedRoutine) {
        return (
            <div className="exercise-list-container">
                <div className="exercise-list-empty">
                    <p>Selecciona una rutina para ver sus ejercicios</p>
                </div>
            </div>
        );
    }

    return (
        <div className="exercise-list-container">
            <div className="exercise-list-header">
                <h2>{selectedRoutine.name}</h2>
                <button className="btn-new" onClick={onStartCreating} disabled={isCreating}>
                    <i className="bi bi-plus"></i> Nuevo Ejercicio
                </button>
            </div>

            <div className="exercise-list">
                {isCreating && (
                    <ExerciseRoutineItem
                        exercise={null}
                        availableExercises={availableExercises}
                        routineId={selectedRoutine.id}
                        isEditing={true}
                        onSave={(data) => { onCreateExercise(data); onCancelCreating(); }}
                        onCancel={onCancelCreating}
                        onDelete={() => {}}
                    />
                )}

                {routineExercises.map(exercise => (
                    <ExerciseRoutineItem
                        key={exercise.id}
                        exercise={exercise}
                        availableExercises={availableExercises}
                        routineId={selectedRoutine.id}
                        isEditing={false}
                        onSave={(data) => onUpdateExercise(exercise.id, data)}
                        onCancel={() => {}}
                        onDelete={() => onDeleteExercise(exercise.id)}
                    />
                ))}

                {routineExercises.length === 0 && !isCreating && (
                    <div className="exercise-list-empty">
                        <p>No hay ejercicios en esta rutina</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default ExerciseRoutineList;
