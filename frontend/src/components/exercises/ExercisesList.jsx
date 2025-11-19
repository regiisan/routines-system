// src/components/exercises/ExercisesList.js

import React from 'react';
import ExerciseItem from './ExerciseItem';

const ExercisesList = ({ exercises, isCreating, onCreateExercise, onUpdateExercise, onDeleteExercise, onStartCreating, onCancelCreating, availableMuscles}) => {

    return (
        <div className="exercise-list-container" style={{ maxWidth: "900px", margin: "0 auto" }}>
            <div className="exercise-list-header">
                <h2>Lista de Ejercicios</h2>
                <button className="btn-new" onClick={onStartCreating} disabled={isCreating}>
                    <i className="bi bi-plus"></i> Nuevo Ejercicio
                </button>
            </div>

            <div className="exercise-list">
                {isCreating && (
                    <ExerciseItem
                        exercise={null}
                        isEditing={true}
                        onSave={(data) => { onCreateExercise(data); onCancelCreating(); }}
                        onCancel={onCancelCreating}
                        onDelete={() => {}}
                        availableMuscles={availableMuscles}
                    />
                )}

                {exercises.map(exercise => (
                    <ExerciseItem
                        key={exercise.id}
                        exercise={exercise}
                        isEditing={false}
                        onSave={(data) => onUpdateExercise(exercise.id, data)}
                        onCancel={() => {}}
                        onDelete={() => onDeleteExercise(exercise.id)}
                        availableMuscles={availableMuscles}
                    />
                ))}
            </div>
        </div>
    );
};

export default ExercisesList;