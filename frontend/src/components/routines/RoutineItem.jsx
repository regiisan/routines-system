import React, {useState, useEffect, useRef} from 'react';
import Swal from "sweetalert2";

const RoutineItem = ({ routine,  isEditing: initialIsEditing, isSelected, onSave, onCancel, onSelect, onDelete }) => {

    const [isEditing, setIsEditing] = useState(initialIsEditing);
    const [name, setName] = useState(routine?.name || '');
    const inputRef = useRef(null);

    useEffect(() => {
        if (isEditing && inputRef.current) {
            inputRef.current.focus();
        }
    }, [isEditing]);

    const handleSave = () => {
        if (name.trim()) {
            onSave({name: name.trim()});
            setIsEditing(false);
        } else {
            Swal.fire({
                icon: "warning",
                title: "Campos incompletos",
                text: "El nombre de la rutina es requerido.",
            });
        }
    };

    const handleCancel = () => {
        setName(routine?.name || '');
        setIsEditing(false);
        onCancel();
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            handleSave();
        } else if (e.key === 'Escape') {
            handleCancel();
        }
    };

    if (isEditing) {
        return (
            <form className="routine-item editing" onSubmit={(e) => {e.preventDefault(); handleSave(); }}>
                <input
                    ref={inputRef}
                    type="text"
                    className="routine-input"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="Nombre de la rutina"
                />
                <div className="routine-actions">
                    <button className="btn-icon btn-save" onClick={handleSave} title="Guardar">
                        <i className="bi bi-check-lg"></i>
                    </button>
                    <button className="btn-icon btn-cancel" onClick={handleCancel} title="Cancelar">
                        <i className="bi bi-x-lg"></i>
                    </button>
                </div>
            </form>
        );
    }

    return (
        <div className={`routine-item ${isSelected ? 'selected' : ''}`} onClick={onSelect}>
            <span className="routine-name">{routine.name}</span>
            <div className="routine-actions">
                <button
                    className="btn-icon btn-edit"
                    onClick={(e) => {
                        e.stopPropagation();
                        setIsEditing(true);
                    }}
                    title="Editar"
                >
                    <i className="bi bi-pencil-fill bi-xs"></i>
                </button>
                <button
                    className="btn-icon btn-delete"
                    onClick={(e) => {
                        e.stopPropagation();
                        onDelete();
                    }}
                    title="Eliminar"
                >
                    <i className="bi bi-trash3-fill"></i>
                </button>
            </div>
        </div>
    );
};

export default RoutineItem;
