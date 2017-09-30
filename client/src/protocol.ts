import 'whatwg-fetch'

const jsonParsingError = {
  error: 'Parsing Error',
  message: 'Impossible to parse server`s response'
}

const filterStatus = (response: Response) => {
  if (response.status >= 200 && response.status < 300) {
    return response.json()
  } else {
    return response.json()
      .then(j => Promise.reject(j))
      .catch(err => Promise.reject(err instanceof Error ? jsonParsingError : err))
  }
}

interface RequestOptions {
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH'
  body?: {}
  headers?: HeadersInit
}

export function fetchJson<T>(url: string, options?: RequestOptions) {

  const jsonBody = (options && options.body) ? JSON.stringify(options.body) : undefined

  const newOptions: any = {
    method: options ? options.method : 'GET',
    body: jsonBody,
    headers: {
      'content-type': 'application/json',
      'pragma': 'no-cache',
      'cache-control': 'no-cache'
    },
    credentials: 'include'
  }

  // tslint:disable:no-any
  return fetch(url, newOptions)
    .then(filterStatus)
    .then((value: any) => value as T)
}
