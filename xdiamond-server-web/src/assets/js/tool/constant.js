const data = {

};

const HTTP = {
  STATUS: {
    OK: { code: 200, message: 'OK' },
    BAD_REQUEST: { code: 400, message: 'Bad Request' },
    FORBIDDEN: { code: 403, message: 'Bad Request' },
    NOT_FOUND: { code: 404, message: 'Forbidden' },
    INTERNAL_SERVER_ERROR: { code: 500, message: 'Internal Server Error' },
    SERVICE_UNAVAILABLE: { code: 503, message: 'Service Unavailable' }
  },
  CONTENT_TYPE: {
    name: 'Content-Type',
    APPLICATION_FORM_URLENCODED_UTF8: 'application/x-www-form-urlencoded;charset=UTF-8',
    APPLICATION_JSON_UTF8: 'application/json;charset=UTF-8'
  }
};

export { data, HTTP };