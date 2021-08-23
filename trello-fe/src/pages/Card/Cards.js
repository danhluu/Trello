import React, { useState } from 'react';
import { PlusOutlined, CloseOutlined } from '@ant-design/icons';
import { Modal, Typography } from 'antd';
import { createTask, testApi } from '../../Api/func/user';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Draggable } from 'react-smooth-dnd';
import './Card.css';
import 'antd/dist/antd.css'
import Task from '../Comment/Task'
import { useEffect } from 'react';


function Cards(props) {
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [open, setOpen] = useState(false);
    const [workTask, setWorkTask] = useState("");
    const [task, settask] = useState("");
    const [user, setuser] = useState({});

    const {onCardDrop} = props;
    const showModal = (card) => {
        settask(card);
        setIsModalVisible(true);
        // console.log(card);
    };
    const handleOk = () => {
        setIsModalVisible(false);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const onSubmit = async () => {
        await createTask({
            workListId: props.workListId,
            title: workTask
        });
        
        setOpen(false);
        props.reload();
    }
    const getUserInfor = async ()=>{
        const rest=await testApi({
        });
        return rest.data;
    }
    useEffect(() => {
        const getAll = async ()=>{
            //user
            const user = await getUserInfor();
            if(user) {
                console.log("Thong tin nguoi dung:",user);
                setuser(user);
            }
        }
        getAll();
    }, [])

    return (
        <div>
            <Container
                groupName="col"
                onDrop={dropResult => onCardDrop(props.workListId, dropResult)}
                getChildPayload={index => props.task[index]
                }
                dragClass="card-ghost"
                dropClass="card-ghost-drop"

                dropPlaceholder={{
                    animationDuration: 150,
                    showOnTop: true,
                    className: 'drop-preview'
                }}
                dropPlaceholderAnimationDuration={200}>
                {props.task.map((card, index) => (
                    <Draggable key={index}>
                        <div className="card-item" key={index}>
                            <button className="typography btn-card" onClick={()=>showModal(card)}>{card.title}</button>
                        </div>
                    </Draggable>
                ))
                }
            </Container>
            <div className="workList">
                {open ? (
                    <div>
                        <input onChange={e => setWorkTask(e.target.value)} type="text" required className="input" placeholder="Enter a title"></input>
                        <button type="button" className="button" onClick={onSubmit} >Add task</button>
                        <CloseOutlined onClick={() => setOpen(false)} style={{ cursor: 'pointer', marginLeft: '80px', fontSize: '20px' }} />
                    </div>
                ) : (
                    <button onClick={() => setOpen(!open)} className="add-task" > <PlusOutlined /> Add task</button>
                )}
            </div>
            <Modal width={1000} centered title="" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
                <Task obj={task} user={user} />
            </Modal>
        </div >
    )
}

export default Cards;
