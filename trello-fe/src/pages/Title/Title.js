import React, { useEffect, useState } from 'react';
import { renameWorkList } from '../../Api/func/user';

function Title(props) {
    const [open, setOpen] = useState(false);
    const [newTitle, setNewTitle] = useState(props.title);

    useEffect(()=>{
        setNewTitle(props.title);
    }, [props.title])
    const handleOnBlur = () => {
        updateTitle(props.listId);
        setOpen(false);
        props.resetTitle(props.listId, newTitle);
    }
    const updateTitle = (id) => {
        renameWorkList({
            workListId: id,
            title: newTitle
        });
    }
    return (
        <div>
            {open ? (
                <input onChange={e => setNewTitle(e.target.value)} value={newTitle} onBlur={handleOnBlur} />
            ) : (
                <header onClick={() => setOpen(!open)} style={{ cursor: 'pointer' }}>{newTitle}</header>
            )}
        </div>
    )

}

export default Title
