
import { PostLogin, PutData, PostData, DeleteData, GetData, PostRegister } from '../helpers';
import url from '../url'

export const loginApi = async (body) =>
    PostLogin(url.urlLogin, body)
        .then(res => res)
        .catch(err => err)


export const testApi = async (body) =>
    GetData(url.urlTest, body)
        .then(res => res)
        .catch(err => err)

export const registerApi = async (body) =>
    PostRegister(url.urlRegiter, body)
        .then(res => res)
        .catch(err => err)

export const deleteBoardApi = async (body) =>
    DeleteData(url.urlBoardDelete, body)
        .then(res => res)
        .catch(err => err)
export const getAllBoard = async (body) =>
    GetData(url.urlGetAllBoard, body)
        .then(res => res)
        .catch(err => err)

export const getAllWorkList = async (body) =>
    GetData(url.urlGetAllWorkList, body)
        .then(res => res)
        .catch(err => err)

export const createBoard = async (body) =>
    PostData(url.urlCreateBoard, body)
        .then(res => res)
        .catch(err => err)
export const createWorkList = async (body) =>
    PostData(url.urlCreateWorkList, body)
        .then(res => res)
        .catch(err => err)
export const updateDisplayOrderWorkList = async (body) =>
    PutData(url.updateDisplayOrderWorkList, body)
        .then(res => res)
        .catch(err => err)
export const renameWorkList = async (body) =>
    await PutData(url.urlRenameWorkList, body)
        .then(res => res)
        .catch(err => err)
export const createTask = async (body) =>
    PostData(url.urlCreateTask, body)
        .then(res => res)
        .catch(err => err)
export const updateDisplayOrderTask = async (body) =>
    PutData(url.urlupdateDisplayOrderTask, body)
        .then(res => res)
        .catch(err => err)
export const deleteWorkList = async (body) =>
    DeleteData(url.urlDeleteWorkList, body)
        .then(res => res)
        .catch(err => err)
export const getComments = async (body) =>
    GetData(url.urlGetAllComment, body)
        .then(res => res)
        .catch(err => err)
export const postCommentApi = async (body) =>
    PostData(url.urlPostComment, body)
        .then(res => res)
        .catch(err => err)
export const getAllUserBoard = async (body) =>
    GetData(url.urlGetAllUserBoard, body)
        .then(res => res)
        .catch(err => err)
export const getAllUserToAddToBoard = async (body) =>
    GetData(url.urlGetAllUserToAddToBoard, body)
        .then(res => res)
        .catch(err => err)
export const postUserToBoardApi = async (body) =>
    PostData(url.urlPostUserToBoard, body)
        .then(res => res)
        .catch(err => err)
export const getAllUserInTask = async (body) =>
    GetData(url.urlGetAllUserInTask, body)
        .then(res => res)
        .catch(err => err)
export const getUsersToAddToTask = async (body) =>
    GetData(url.urlGetUsersToAddToTask, body)
        .then(res => res)
        .catch(err => err)
export const postUserToTaskApi = async (body) =>
    PostData(url.urlPostUserToTask, body)
        .then(res => res)
        .catch(err => err)
export const updateImageTask = async (body) =>
    PutData(url.urlPutImageTask, body)
        .then(res => res)
        .catch(err => err)
export const getDeadlineByTaskIdApi = async (body) =>
    GetData(url.urlGetDeadlineByTaskId, body)
        .then(res => res)
        .catch(err => err)
export const postDeadlineToTaskApi = async (body) =>
    PostData(url.urlPostDeadline, body)
        .then(res => res)
        .catch(err => err)
export const updateDeadlineApi = async (body) =>
    PutData(url.urlUpdateDealine, body)
        .then(res => res)
        .catch(err => err)
export const deleteDeadlineApi = async (body) =>
    DeleteData(url.urlDeleteDeadline, body)
        .then(res => res)
        .catch(err => err)
export const deleteUserToBoardApi = async (body) =>
    DeleteData(url.urlDeleteUserBoard, body)
        .then(res => res)
        .catch(err => err)
export const deleteUserToTaskApi = async (body) =>
    DeleteData(url.urlDeleteUserToTask, body)
        .then(res => res)
        .catch(err => err)