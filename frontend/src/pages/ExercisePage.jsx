// src/pages/ExercisePage.js
import React, { useEffect, useState } from 'react';
import ExerciseService from "../services/ExerciseService";
import ExerciseList from "../components/exercises/ExerciseList";
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
        return ExerciseService.createExercise(data)
            .then(() => listExercises())
            .catch( (error) => {
                Swal.fire({
                    icon: "error",
                    title: "Nombre duplicado",
                    text: "Ya existe un ejercicio con ese nombre."
                });
                throw error;
            });
    };

    const handleUpdate = (id, data) => {
        return ExerciseService.updateExercise(id, data)
            .then(() => listExercises())
            .catch((error) => {
                Swal.fire({
                    icon: "error",
                    title: "No se pudo actualizar",
                    text: "Ya existe un ejercicio con ese nombre.\n",
                });
                throw error;
            });
    }

    const handleDelete = (id) => {
        return ExerciseService.deleteExercise(id)
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
                <ExerciseList
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