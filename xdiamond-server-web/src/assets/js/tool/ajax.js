import { HTTP } from './constant';
import axios from 'axios';
import qs from 'qs';

/**
 * axios请求工具
 *
 * @param URL
 * @returns {Ajax}
 * @constructor
 */
Ajax = {
  get: (url, headers, data) => {
    if (url.includes('?')) {
      
    }
    axios.get(url, {
      headers: headers,
      transformResponse: data => data.data,
    });
  }
}

/**
 * POST请求
 *
 * @param url
 * @param data
 * @param callBack
 * @param failer
 */
Ajax.prototype.post = function (url, data, callBack, failer) {
  if (typeof arguments[1] === 'object') {
    data = arguments[1];
  } else if (typeof arguments[1] === 'function') {
    data = {};
    callBack = arguments[1];
  }
  url = url || '';
  data = data || {};
  // data = Object.assign(data, this.commonData);
  const combine = this.combine(url, data);
  axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
  axios.post(combine.url, qs.stringify(data))
    .then(function (response) {
      if (response.status === HTTP.STATUS.OK.code) {
        callBack(response.data);
      } else {
        failer && failer(response);
      }
    })
    .catch(function (error) {
      console.error(error);
    });
};

/**
 * GET请求
 *
 * @param url
 * @param data
 * @param callBack
 * @param failer
 */
Ajax.prototype.get = function (url, data, callBack, failer) {
  if (typeof arguments[1] === 'object') {
    data = arguments[1];
  } else if (typeof arguments[1] === 'function') {
    data = {};
    callBack = arguments[1];
  }
  url = url || '';
  data = data || {};
  // data = Object.assign(data, this.commonData);
  let isurl = this.combine(url, {}).url;
  data = this.getData(data, 'getData');
  isurl += data ? ('?' + data) : '';
  axios.get(isurl, data)
    .then(function (response) {
      if (response.status === HTTP.STATUS.OK.code) {
        callBack(response.data);
      } else {
        failer && failer(response)
      }
    })
    .catch(function (error) {
      console.error(error);
    });
};

export default Ajax;