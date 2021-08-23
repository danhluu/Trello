import React, { useEffect, useState,useRef } from 'react';
import { StarOutlined, LockOutlined , PlusOutlined} from '@ant-design/icons';
import {Button, Layout, Select, Avatar,Input ,Modal} from 'antd';
// import api from './callApi/baseUrl'
import Form from 'antd/lib/form/Form';
import { v4 as uuidv4 } from 'uuid';
import './header2.css'
import { deleteUserToBoardApi, getAllUserBoard, getAllUserToAddToBoard, postUserToBoardApi,testApi } from '../Api/func/user';

function Header2(id) {
    const styleHead2 = {
        position:"relative",
        background: '#6D8DDF',
        height: '60px',
        marginTop: '6px',
        padding: '0px'
    };
    const styleButton = {
        margin:'auto 5px',
        background: '#B9CFFB',
        color:'#fff', 
        fontWeight:'bold', 
        height:'40px',
        border: 'none',
    };
    const styleAvatar = {
        width:"40px",
        height:"40px",
        color: '#000',
        textAlign:"center",
        backgroundColor: '#fff',
        fontSize: '12px',
        fontWeight: '500',
        // margin: 'auto 1px auto 0',
        borderRadius: '50%'
    };

    const invite_popup = {
        width: "300px",
        height: "400px",
        position: 'absolute',
        top: '33px',
        left: '4px',
        backgroundColor: '#ffffff',
        border: 'solid 1px #e7eaf0',
        borderRadius: '5px',
        zIndex: '1'
    }

    //state
    const [userSearch, setuserSearch] = useState([]);
    const [userAdded, setuserAdded] = useState([]);
    const [email, setemail] = useState("");
    const [error, seterror] = useState("1");
    const [users, setusers] = useState([]);//danh sach nguoi trong bang
    const [userDetail, setuserDetail] = useState({})
    const [userInfor, setuserInfor] = useState({})
        //modal
    const [isModalUserDetail, setisModalUserDetail] = useState(false);

    //ref
    const textInput = useRef(null);


    //ẩn hiện form thêm người dùng vào bảng
    const btnInviteOnclick = ()=>{
        var x = document.getElementById("invitePopup");
        document.getElementById("focus").focus();
        x.classList.toggle("isHide");
        const userDetail = document.getElementById("detail-user");
        userDetail.classList.add("isHide");
        setuserSearch([]);
        setuserAdded([]);
        seterror("1");
        textInput.current.focus();
        // setemail("");
    }

    //đóng invite
    const btnCloseInviteOnClick = ()=>{
        var x = document.getElementById("invitePopup");
        x.classList.add("isHide");
    }

    //change email
    const EmailOnChange = (e) =>{
        if(e.target.value){
            setemail(e.target.value);
        }
    }


    //Đóng popup danh sách người dùng trong bảng
    const handleCancelDead = () => {
        setisModalUserDetail(false)
    };

    //Đóng popup danh sách người dùng trong bảng - X
    const btnCloseListUser = ()=>{
        const dom = document.getElementById("popup-list-users");
        dom.classList.add("isHide");
    }

    //Tìm Kiếm người dùng
    const btnSearchOnClick = async (e)=>{
        try{
            var users = await getAllUserToAddToBoard({
                boardId: id.id,
                keyword: email
            });
            
            console.log(users.data);
            if(users.data.length>0){
                setuserSearch(users.data);
                seterror("1");
            }
            else {
                seterror("2");
                setuserSearch([]);
            }
        }catch(ex){
            console.log(ex);
            seterror("Không tìm thấy kết quả");
            setuserSearch([]);
        }
    }

    //Thêm vào danh sách add
    const addToListInvite = (e) =>{
        // console.log("obj invite: ",e)
        if(!userAdded.includes(e)){
            setuserAdded([...userAdded,e]);
        }
        // console.log(userAdded);
    }

    //Xoá khỏi danh sách mời vào bảng
    const removeToListInvite = (obj) =>{
        console.log("obj Remove: ",obj)
        var arr = userAdded;
        console.log("arr: ",arr)
        for(let i =0;i<arr.length;i++){
            if(obj.id==arr[i].id){
                arr.splice(i,1);
                // console.log(arr[i]);
                
            }
        }
        console.log("Sau khi remove: ",userAdded)
        setuserAdded([...arr]);
        
    }

    //thêm người vào bảng
    const btnAddUserOnClick = async (e)=>{
        if(userAdded.length>0){
            for(let i = 0;i<userAdded.length;i++){
                var u_id = userAdded[i].id;
                var b_id=id.id;
                var user_b = {
                    userId: u_id,
                    // boardId: b_id,
                    fullName:userAdded[i].fullName,
                    email:userAdded[i].email
                }
                // console.log(user_b)
                await postUserToBoardApi({
                    userId: u_id,
                    boardId: b_id
                }).then(res=>{
                    console.log("res: ",res);
                    setusers([...users,user_b])
                });
                // api.post("user_b",user_b).then(res =>{
                //     console.log("res: ",res);
                // }).catch(e =>{
                //     console.log("ex: ",e)
                // });
            }
            btnCloseInviteOnClick();
        }
    }

    //Hien thi danh sach nguoi dung
    const showModelDetailUser = ()=>{
        //An Hien danh sach
        const dom = document.getElementById("popup-list-users");
        dom.classList.toggle("isHide");

        //an chi tiet
        const domDetail = document.getElementById("detail-user");
        domDetail.classList.add("isHide");
    }

    const { Header} = Layout;
    const { Option } = Select;

    
    // Get all user
    const getAllUsers = async ()=>{
        const users = await getAllUserBoard({
            boardId:id.id
        });
        return users;
        // const res = await api.get('/user_board');
        // return res;
    }

    // Chi tiết user
    const DetailUser = (obj)=>{
        //an danh sach
        const domList = document.getElementById("popup-list-users");
        domList.classList.add("isHide");
        //hien thi chi tiet
        const dom = document.getElementById("detail-user");
        dom.classList.toggle("isHide");
        //an invite
        const invite = document.getElementById("invitePopup");
        invite.classList.add("isHide")
        console.log(obj)

        setuserDetail(obj);
    }

    

    //Đóng chi tiết người dùng
    const btnCloseOnClick = ()=>{
        const dom = document.getElementById("detail-user");
        dom.classList.add("isHide");
    }

    //Xử lý Rời khỏi bảng
    const btnLeaveUserOnClick = async()=>{
        console.log("Chi tiết ngừi dùng rời khỏi bảng: ",userDetail)
        var arr = users;
        await deleteUserToBoardApi({
            userId:userDetail.id,
            boardId: id.id
        }).then(async()=>{
            const allUsers = await getAllUsers();
            if(allUsers){
                const dom = document.getElementById("detail-user");
                dom.classList.add("isHide");
                setusers(allUsers.data);
            }
                
        });
    }

    // useEffect
    useEffect(async ()=>{
        const getAll = async () =>{
            const allUsers = await getAllUsers();
            console.log("user: ",allUsers);
            if(allUsers)
                setusers(allUsers.data);
        }
        console.log(id.id)
        // const timer = setInterval(1000)
        getAll();
        // return () => clearInterval(timer);
    }, []);

    // if(users[0])
    //     console.log(users[0].username.charAt(0));


    return (
        <Header style={styleHead2}>
            <div style={{margin:'auto 20px', display:'flex', justifyContent:'space-between'}}>
                    <div>
                        <Select defaultValue="Board" style={{ width: 110, margin:'auto 5px auto 0', color:'#000', fontWeight:'500'}} >
                            <Option value="Board">Board</Option>
                            <Option value="Calendar">Calendar</Option>               
                        </Select>
                        <Button style={styleButton}>Name</Button>
                        <Button style={styleButton}><StarOutlined /></Button>
                        <Button style={styleButton}><LockOutlined /> Private</Button>
                        {
                            users.map((user, index)=>{
                                return (index<2)?
                                    <Button onClick={()=>DetailUser(user)} key={user.id} style={styleAvatar}>{(user.fullName)?user.fullName.charAt(0):""}</Button>
                                    :"";
                                
                            })
                        }

                        {/* Chi tiết người dùng */}
                        <div id="detail-user" className="isHide">
                            <button onClick={btnCloseOnClick} className="btnClose">X</button>
                            <div  className="flex detail-user__header">
                                <div className="header__avatar">
                                    <Avatar className="circle_avatar">{(userDetail.fullName)?userDetail.fullName.charAt(0).toUpperCase():""}</Avatar>
                                </div>

                                <div className="infor-user">
                                    <div className="infor-user_name">{userDetail.fullName}</div>
                                    <div className="infor-user_email">{userDetail.email}</div>
                                </div>
                            </div>
                            {/* 3 chúc năng */}
                            <div className="btn-option-user">
                                <Button className="btn">Thay Đổi quyền</Button>
                            </div>
                            <div className="btn-option-user">
                                <Button className="btn">Xem hoạt đông của thành viên trong bảng</Button>
                            </div>
                            <div className="btn-option-user">
                                <Button onClick={btnLeaveUserOnClick} className="btn">Rời khỏi bảng</Button>
                            </div>

                        </div>

                        
                        <Button onClick={showModelDetailUser} style={styleAvatar} className="btn-plus-user">
                            <PlusOutlined/>{((users.length-2)>0)?(users.length-2):""} 
                        </Button>
                        <div id="popup-list-users" className="popup-list-users isHide">
                            <button onClick={btnCloseListUser} className="btnCloseListUser">X</button>
                            <div style={{margin:"10px 0 0 20px",lineHeight:"20px"}} >Danh sách người dùng</div>
                            <hr style={{width:"90%", margin:"10px 0 0 10px"}} />
                            {
                                users.map((user, index)=>(
                                    <Button onClick={()=>DetailUser(user)} key={user.id} style={styleAvatar}>
                                        {(user.fullName)?user.fullName.charAt(0):""}
                                    </Button>
                                ))
                            }
                        </div>
                        {/* Button Invite */}
                        <span style={{position:"relative"}}>
                            <Button onClick={btnInviteOnclick}  style={styleButton}>
                                Invite
                            </Button>
                            <div id="invitePopup" style={invite_popup} className="isHide">
                                <button onClick={btnCloseInviteOnClick} className="btnClose" style={{lineHeight:"normal"}}>X</button>
                                <div style={{height:"20px",textAlign:"center",lineHeight:"20px",marginTop:"5px", alignItems:"center"}}>
                                    Mời vào bảng
                                </div>
                                <hr style={{width:"90%", margin:"10px 0 0 10px"}}/>
                                <div>
                                   <input id="focus" ref={textInput} onChange={EmailOnChange} style={{height:"32px", marginLeft:"13px"}} type="text" placeholder="Địa chỉ email hoặc tên"/>
                                   <Button style={{marginLeft:"10px"}} onClick={btnSearchOnClick}>Tìm Kiếm</Button>
                                </div>

                                <div>
                                    <ul style={{listStyleType:"none",margin:"0 0 0 20px",padding:"0"}}>
                                    {
                                        userSearch.map((obj,index)=>(
                                            <li key={obj.id} style={{height:"25px", marginTop:"5px"}}>
                                                <Button 
                                                    style={{
                                                        position:"fixed",
                                                        height:"25px",
                                                        lineHeight:"18px",
                                                        border:"none", 
                                                        borderRadius:"10px", 
                                                        backgroundColor:"#6d8ddf", 
                                                        color:"#fff"
                                                    }}
                                                    onClick={()=>addToListInvite(obj)}
                                                >
                                                    {obj.email}
                                                </Button>
                                            </li>
                                        ))
                                    }
                                    </ul>
                                </div>
                                
                                

                                <div style={{textAlign:"center",fontSize:"16px"}}>{(error!="1")? "Không tìm thấy":"" }</div>
                                <hr style={{width:"90%", margin:"10px 0 0 10px"}}/>
                                
                                <div>
                                    <ul style={{listStyleType:"none",margin:"0 0 0 20px",padding:"0"}}>
                                        {
                                            userAdded.map((obj,index)=>(
                                                <li key={obj.id} style={{height:"25px", marginTop:"5px"}}>
                                                    <Button 
                                                        style={{
                                                            position:"fixed",
                                                            height:"25px",
                                                            lineHeight:"18px",
                                                            border:"none", 
                                                            borderRadius:"10px", 
                                                            backgroundColor:"#b9cffb", 
                                                            color:"#fff"
                                                        }}
                                                        onClick={()=>removeToListInvite(obj)}
                                                    >
                                                        {obj.email}
                                                    </Button>
                                                </li>
                                            ))
                                        }
                                    </ul>
                                </div>

                                <div style={{position:"absolute", bottom:"10px", left:"25px"}}>
                                    <Button onClick={btnAddUserOnClick} 
                                        className="btnAddUser" 
                                        style={{width:"250px",backgroundColor:"#0079bf", color:"#fff"}} 
                                        disabled={(userAdded.length==0)}
                                    >
                                        THÊM VÀO BẢNG
                                    </Button>
                                </div>
                                

                            </div>
                            
                        </span>
                        
                        {/* <span>Truyền board ID vào header2: {id.id}</span> */}
                    </div>       
                    <div>
                        <Button style={styleButton}>Menu</Button>
                    </div>
            </div>
            
        </Header>
    )
}

export default Header2
