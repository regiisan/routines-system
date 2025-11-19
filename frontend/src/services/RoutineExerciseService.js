import axios from "axios";

const BASE_URL = "http://localhost:8080/api/routine-exercises";

class RoutineExerciseService {

    getAllRoutinesExercisesByRoutine(routineId) {
        return axios.get(`${BASE_URL}/routine/${routineId}`);
    }

    createRoutineExercise(routineExercise){
        return axios.post(BASE_URL, routineExercise);
    }

    getRoutineExerciseById(routineExerciseId){
        return axios.get(`${BASE_URL}/${routineExerciseId}`);
    }

    updateRoutineExercise(routineExerciseId, routineExercise){
        return axios.put(`${BASE_URL}/${routineExerciseId}`, routineExercise);
    }

    deleteRoutineExercise(routineExerciseId){
        return axios.delete(`${BASE_URL}/${routineExerciseId}`);
    }

}

export default new RoutineExerciseService();
