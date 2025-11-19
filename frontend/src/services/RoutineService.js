import axios from "axios";

const BASE_URL = "http://localhost:8080/api/routines";

class RoutineService {

    getAllRoutines() {
        return axios.get(BASE_URL);
    }

    createRoutine(routine){
        return axios.post(BASE_URL, routine);
    }

    getRoutineById(routineId){
        return axios.get(`${BASE_URL}/${routineId}`);
    }

    updateRoutine(routineId, routine){
        return axios.put(`${BASE_URL}/${routineId}`, routine);
    }

    deleteRoutine(routineId){
        return axios.delete(`${BASE_URL}/${routineId}`);
    }


}

export default new RoutineService();
