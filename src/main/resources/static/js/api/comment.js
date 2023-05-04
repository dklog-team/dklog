const add = (requestData) => {
    const uri = `/comments`;
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    }
    return request.post(uri, requestData, config);
}

const remove = (requestData) => {
    const uri = `/comments/${requestData.commentId}`;
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
        params: {
            postId: requestData.postId,
            memberId: requestData.memberId,
        }
    }
    return request.delete(uri, config);
}

const update = (requestData) => {
    const uri = `/comments/${requestData.commentId}`;
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    }
    return request.put(uri, requestData, config);
}
// const getAuthCode = (requestData) => {
//     const uri = `/student/auth/code`
//     const config = {
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         params: requestData
//     }
//     return request.get(uri, config)
// }
//
// const submit = (requestData) => {
//     const uri = `/student/auth/code/submit`
//     const config = {
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         params: requestData
//     }
//     return request.get(uri, config)
// }