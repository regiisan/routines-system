import React, { useEffect, useState } from 'react';
import RoutineList from '../components/routines/RoutineList';
import ExerciseRoutineList from '../components/exercises/ExerciseRoutineList';
import RoutineService from "../services/RoutineService";
import ExerciseService from "../services/ExerciseService";
import RoutineExerciseService from "../services/RoutineExerciseService";

const RoutinesPage = () => {
    const [routines, setRoutines] = useState([]);
    const [exercises, setExercises] = useState([]);
    const [selectedRoutine, setSelectedRoutine] = useState(null);
    const [routineExercises, setRoutineExercises] = useState([]);
    const [isCreatingRoutine, setIsCreatingRoutine] = useState(false);
    const [isCreatingExercise, setIsCreatingExercise] = useState(false);

    useEffect(() => {
        listRoutines();
        listExercises();
    }, []);

    const listRoutines = () => {
        RoutineService.getAllRoutines()
            .then(response => {
                setRoutines(response.data);
            }).catch(error => {console.error(error)});
    }

    const listExercises = () => {
        ExerciseService.getAllExercises()
            .then(response => setExercises(response.data))
            .catch(console.error);
    }

    const listRoutineExercises = (routineId) => {
        RoutineExerciseService.getAllRoutinesExercisesByRoutine(routineId)
            .then(response => setRoutineExercises(response.data))
            .catch(console.error);
    };

    const handleSelectRoutine = (response) => {
        setSelectedRoutine(response);
        listRoutineExercises(response.id);
        setIsCreatingExercise(false);
    }

    const handleCreateRoutine = (data) => {
        RoutineService.createRoutine(data)
            .then(res => {
                setRoutines(prev => [...prev, res.data]);
                setSelectedRoutine(res.data);
                setIsCreatingRoutine(false);
            })
            .catch(console.error);
    };

    const handleUpdateRoutine = (id, data) => {
        RoutineService.updateRoutine(id, data)
            .then(() => listRoutines())
            .catch(console.error);
    };

    const handleDeleteRoutine = (id) => {
        RoutineService.deleteRoutine(id)
            .then(() => {
                listRoutines();
                if (selectedRoutine && selectedRoutine.id === id) {
                    setSelectedRoutine(null);
                    setRoutineExercises([]);
                }
            })
            .catch(console.error);
    }

    const handleCreateExercise = (data) => {
        RoutineExerciseService.createRoutineExercise(data)
            .then(() => listRoutineExercises(data.routineId))
            .catch(console.error);
    }

    const handleUpdateExercise = (id, data) => {
        RoutineExerciseService.updateRoutineExercise(id, data)
            .then(() => listRoutineExercises(selectedRoutine.id))
            .catch(console.error);
    };

    const handleDeleteExercise = (id) => {
        RoutineExerciseService.deleteRoutineExercise(id)
            .then(() => listRoutineExercises(selectedRoutine.id))
            .catch(console.error);
    }

    return (
        <div className="container py-4">
            <div className="row justify-content-center">
                <div className="col-lg-4">
                    <RoutineList
                        routines={routines}
                        selectedRoutine={selectedRoutine}
                        isCreating={isCreatingRoutine}
                        onSelectRoutine={handleSelectRoutine}
                        onCreateRoutine={handleCreateRoutine}
                        onUpdateRoutine={handleUpdateRoutine}
                        onDeleteRoutine={handleDeleteRoutine}
                        onStartCreating={() => setIsCreatingRoutine(true)}
                        onCancelCreating={() => setIsCreatingRoutine(false)}
                    />
                </div>

                <div className="col-lg-8">
                    <ExerciseRoutineList
                        selectedRoutine={selectedRoutine}
                        routineExercises={routineExercises}
                        availableExercises={exercises}
                        isCreating={isCreatingExercise}
                        onCreateExercise={handleCreateExercise}
                        onUpdateExercise={handleUpdateExercise}
                        onDeleteExercise={handleDeleteExercise}
                        onStartCreating={() => setIsCreatingExercise(true)}
                        onCancelCreating={() => setIsCreatingExercise(false)}
                    />
                </div>
            </div>
        </div>
    );
};

export default RoutinesPage;