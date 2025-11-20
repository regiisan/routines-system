// src/pages/ExercisePage.js
import React, { useEffect, useState } from 'react';
import ExerciseService from "../services/ExerciseService";
import ExercisesList from "../components/exercises/ExercisesList";
import Swal from "sweetalert2";

const ExercisePage = () => {

    const [exercises, setExercises] = useState([]);
    const [isCreating, setIsCreating] = useState(false);
    const [availableMuscles, setAvailableMuscles] = useState([]);

    useEffect(() => {
        listExercises();
        listMuscles();
    }, []);

    const listExercises = () => {
        ExerciseService.getAllExercises()
            .then(response => setExercises(response.data))
            .catch(error => console.error(error));
    }

    const listMuscles = () => {
        ExerciseService.getMuscles()
            .then(res => setAvailableMuscles(res.data))
            .catch(err => console.error(err));
    };

    const handleCreate = (data) => {
        ExerciseService.createExercise(data)
            .then(() => listExercises())
            .catch(console.error);
    }

    const handleUpdate = (id, data) => {
        ExerciseService.updateExercise(id, data)
            .then(() => listExercises())
            .catch(console.error);
    }

    const handleDelete = (id) => {
        ExerciseService.deleteExercise(id)
            .then(() => listExercises())
            .catch(() => {
                Swal.fire({
                    icon: "error",
                    title: "No se pudo eliminar",
                    text: "Este ejercicio está siendo usado en una rutina. Para eliminarlo, primero quitálo de esa rutina.\n",
                });
            });
    }

    return (
        <div className="d-flex">
            <div className="container p-4">
                <ExercisesList
                    exercises={exercises}
                    isCreating={isCreating}
                    onCreateExercise={handleCreate}
                    onUpdateExercise={handleUpdate}
                    onDeleteExercise={handleDelete}
                    onStartCreating={() => setIsCreating(true)}
                    onCancelCreating={() => setIsCreating(false)}
                    availableMuscles={availableMuscles}
                />
            </div>
        </div>
    )
}

export default ExercisePage;