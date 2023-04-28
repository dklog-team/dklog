const getAuthCode = (requestData) => {
    const uri = `/student/auth/code`
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
        params: requestData
    }
    return request.get(uri, config)
}

const submit = (requestData) => {
    const uri = `/student/auth/code/submit`
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
        params: requestData
    }
    return request.get(uri, config)
}