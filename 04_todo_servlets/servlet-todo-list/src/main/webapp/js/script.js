async function deleteTask(contextPath, taskId) {
    let requestParams = {
        method: 'DELETE'
    }
    await fetch(contextPath + '/list?taskId='  + taskId, requestParams)
    location.reload()
}
