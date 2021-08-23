
const root = "http://localhost:8080/";

const url = {
    urlLogin: root + "login",
    urlRegiter: root + "register",
    urlTest: root + "getInfo",
    urlBoardDelete: root + "board/delete",
    urlGetAllBoard: root + "board/get-all",
    urlGetAllWorkList: root + "work-list/get-all",
    urlCreateBoard: root + "board/create",
    urlCreateWorkList: root + "work-list/create",
    urlCreateTask: root + "task/create",
    updateDisplayOrderWorkList: root + "work-list/update-display-order",
    urlRenameWorkList: root + "work-list/rename",
    urlupdateDisplayOrderTask: root + "task/update-display-order",
    urlDeleteWorkList: root + "work-list/delete",
    urlGetAllComment: root + "comment/get-all",
    urlPostComment: root+"comment/create",
    urlGetAllUserBoard: root+"board/get-all-user-in-board",
    urlGetAllUserToAddToBoard:root+"board/get-list-person-to-add-to-board",
    urlPostUserToBoard: root+"board/add-user-to-board",
    urlGetAllUserInTask: root+"task/get-all-person-in-task",
    urlGetUsersToAddToTask: root+"task/get-list-person-to-add-to-task",
    urlPostUserToTask: root+"task/add-person-to-task",
    urlPutImageTask: root+"task/update-image",
    urlGetDeadlineByTaskId: root+"deadline/get-deadline",
    urlPostDeadline: root+"deadline/create",
    urlUpdateDealine:root+"deadline/update",
    urlDeleteDeadline: root+"deadline/delete-deadline",
    urlDeleteUserBoard: root+"board/delete-person-from-board",
    urlDeleteUserToTask: root+"task/delete-person-to-task"
}
export default url;
