const httpStatusCode = axios.HttpStatusCode;
const request = axios.create({
    baseURL: 'http://localhost:8084',
    timeout: 30000,
})

request.interceptors.response.use(
    response => {
        const statuses = [httpStatusCode.Ok, httpStatusCode.Created, httpStatusCode.NoContent]
        if (statuses.includes(response.status)) {
            console.log('Request Success')
        }
        return response
    },
    error => {
        alert(error.response.data.message)
        console.log('exception: ', error.response.data)
        return Promise.reject(error)
    }
)
