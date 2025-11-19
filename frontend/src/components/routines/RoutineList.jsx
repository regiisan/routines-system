import React from 'react';
import RoutineItem from './RoutineItem';

const RoutineList = ({ routines, selectedRoutine, isCreating, onSelectRoutine, onCreateRoutine, onUpdateRoutine, onDeleteRoutine, onStartCreating, onCancelCreating }) => {

    return (
        <div className="routine-list-container">
            <div className="routine-list-header">
                <h2>Mis Rutinas</h2>
                <button className="btn-new" onClick={onStartCreating} disabled={isCreating}>
                    <i className="bi bi-plus"></i> Nueva Rutina
                </button>
            </div>

            <div className="routine-list">
                {isCreating && (
                    <RoutineItem
                        routine={null}
                        isEditing={true}
                        isSelected={false}
                        onSave={(data) => { onCreateRoutine(data); onCancelCreating();}}
                        onCancel={onCancelCreating}
                        onSelect={() => {}}
                        onDelete={() => {}}
                    />
                )}

                {routines.map(routine => (
                    <RoutineItem
                        key={routine.id}
                        routine={routine}
                        isEditing={false}
                        isSelected={selectedRoutine?.id === routine.id}
                        onSave={(data) => onUpdateRoutine(routine.id, data)}
                        onCancel={() => {}}
                        onSelect={() => onSelectRoutine(routine)}
                        onDelete={() => onDeleteRoutine(routine.id)}
                    />
                ))}
            </div>
        </div>
    );
};

export default RoutineList;
