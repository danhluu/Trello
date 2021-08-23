import React, { useEffect, useState, useCallback } from 'react';
import { Button, Dropdown, Modal } from 'antd';
import { createWorkList, getAllWorkList, updateDisplayOrderTask, updateDisplayOrderWorkList, deleteWorkList } from '../../Api/func/user';
import { PlusOutlined, CloseOutlined, DashOutlined } from '@ant-design/icons';
import Cards from '../Card/Cards';
import { Container, Draggable } from 'react-smooth-dnd';
import 'bootstrap/dist/css/bootstrap.min.css';
import Title from '../Title/Title';
import './list.css';
import { applyDrag } from '../../utils/dragDrop';

function List(props) {
    const [lists, setLists] = useState([]);
    const [open, setOpen] = useState(false);
    const [workList, setworkList] = useState();

    const [addId, setAddId] = useState(-1);
    const [removeId, setRemoveId] = useState(-1);
    const [addedIndex, setAddedIndex] = useState(-1);
    const [removedIndex, setRemovedIndex] = useState(-1);

    const fetchData = useCallback(async () => {
        reload();
    }, [])

    useEffect(() => {
        fetchData();
    }, [fetchData])

    const onSubmit = async () => {
        await createWorkList({
            boardId: props.id,
            title: workList
        });
        reload();
        setOpen(false);
    }
    const reload = async () => {
        const res = await getAllWorkList({
            boardId: props.id
        });
        if (res.data != null) {
            setLists(res.data);
        }
    }

    const resetTitle = (workListId, title) =>{
        let newColums = [...lists]
        newColums=  newColums.map(x => {
            if(x.workListId === workListId){
                x.title = title;
            }
            return x;
        })
        setLists(newColums);
    }

    const onColumnDrop = async (dropResult) => {
        let newColums = [...lists]
        newColums = applyDrag(newColums, dropResult)
        setLists(newColums)
        await updateDisplayOrderWorkList({
            boardId: props.id,
            addedIndex: dropResult.addedIndex,
            removeIndex: dropResult.removedIndex
        });
    }

    const updateTask = async () => {
        await updateDisplayOrderTask({
            removeId: removeId,
            removedIndex: removedIndex,
            addId: addId,
            addedIndex: addedIndex
        });
    }
    const onCardDrop = (workListId, dropResult) => {
        if (dropResult.removedIndex !== null || dropResult.addedIndex !== null) {
            let newColums = [...lists]
            let currenColumn = newColums.find(x => x.workListId === workListId)
            currenColumn.tasks = applyDrag(currenColumn.tasks, dropResult)
            setLists(newColums);
            if (dropResult.removedIndex !== null) {
                setRemoveId(workListId);
                setRemovedIndex(dropResult.removedIndex);
            }
            if (dropResult.addedIndex !== null) {
                setAddId(workListId);
                setAddedIndex(dropResult.addedIndex);

            }
            console.log(dropResult);
        }
    }
    if (addedIndex !== -1 && addId !== -1 && removedIndex !== -1 && removeId !== -1) {
        updateTask();
        setAddId(-1);
        setAddedIndex(-1);
        setRemovedIndex(-1);
        setRemoveId(-1);
    }
    // delete workList
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [listId, setListId] = useState("");
    const showModal = (listId) => {
        setListId(listId);
        setIsModalVisible(true);
    };
    const handleCancel = () => {
        setIsModalVisible(false);
    };
    const handleDeleteList = () => {
        deleteList(listId);
        setIsModalVisible(false)
    };
    function deleteList(id) {
        let newColums = [...lists];
        let displayOrder = newColums.find(x => x.workListId === id).displayOrder;
        newColums = newColums.filter(x => x.workListId !== id);
        newColums = newColums.map(x => {
            if (x.displayOrder > displayOrder) {
                x.displayOrder = x.displayOrder - 1;
            }
            return x;
        })
        setLists(newColums);
        deleteWL(id);

    }
    const deleteWL = async (id) => {
        await deleteWorkList({
            workListId: id
        });
    }

    return (
        <div style={{ display: 'flex', background: "green", overflow: 'scroll', height: '82vh' }}>
            <Container
                orientation="horizontal"
                onDrop={onColumnDrop}
                getChildPayload={index => lists[index]}
                dropPlaceholder={{
                    animationDuration: 150,
                    showOnTop: true,
                    className: 'cards-drop-preview'
                }}>
                {lists.map((list, index) => (
                    <Draggable key={index}>
                        <div className="column" >
                            <div className="title" >
                                <Title listId={list.workListId} title={list.title} resetTitle={resetTitle}></Title>
                                <Dropdown trigger="click"
                                    overlay={<Button onClick={() => showModal(list.id)}
                                        style={{ border: 'none', padding: '10px', color: '#000', borderRadius: '3px', cursor: 'pointer' }}
                                    >Delete List
                                    </Button>}
                                    placement="bottomLeft" >
                                    <DashOutlined style={{ cursor: 'pointer' }} />
                                </Dropdown>
                            </div>
                            <div className="card-list">
                                <Cards task={list.tasks} workListId={list.workListId} reload={reload} onCardDrop={onCardDrop} />
                            </div>
                        </div>
                    </Draggable>
                ))}
            </Container>
            <Modal title="Alert !!!" visible={isModalVisible} width={400} onOk={handleDeleteList}
                onCancel={handleCancel}>
                <h3>Do you want to delete this list?</h3>
            </Modal>
            <div>
                {open ? (
                    <div className="box">
                        <input className="input" onChange={e => setworkList(e.target.value)} type="text" placeholder="Enter a title"></input>
                        <button className="button" onClick={onSubmit}>Add list</button>
                        <CloseOutlined onClick={() => setOpen(false)} style={{ cursor: 'pointer', marginLeft: '20px' }} />
                    </div>
                ) : (
                    <Button className="add-new-list" onClick={() => setOpen(!open)}>
                        <PlusOutlined /> Add New List
                    </Button>
                )}
            </div>
        </div>
    )
}

export default List
