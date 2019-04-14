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
const Ajax = {
  // 处理URL
  uriHandler: (url, data) => {
    if (!data) return url;
    url = url + (url.includes('?') ? url.endsWith('?') ? '' : '&' : '?') + qs.stringify(data);
    return url.endsWith('?') || url.endsWith('&') ? url.substring(0, url.length - 1) : url;
  },
  // 插值URL
  uriMatch: (url, ...uri) => {
    const m = url.match(/\{\w+\}/g);
    if (!m) return url;
    if (m.length != uri.length) {
      console.error('url match uri is error.');
      return url;
    }
    for (let i = 0, n = m.length; i < n; i++) {
      url = url.replace(m[i], uri[i]);
    }
    return url;
  },
  // 处理响应
  responseHandler: resp => {
    if (resp.status === HTTP.STATUS.OK.code) return resp.data;
    console.error('http error', resp);
  },
  // GET
  get: function (url, data, ...uri) {
    url = this.uriHandler(url, data);
    url = this.uriMatch(url, ...uri);
    return axios.get(url).then(resp => this.responseHandler(resp));
  },
  // POST FORM
  post: function (url, data, ...uri) {
    url = this.uriMatch(url, ...uri);
    return axios.post(url, qs.stringify(data), {
      headers: { 'Content-Type': HTTP.CONTENT_TYPE.APPLICATION_FORM_URLENCODED_UTF8 }
    }).then(resp => this.responseHandler(resp));
  },
  // POST JSON
  postJson: function (url, data, ...uri) {
    url = this.uriMatch(url, ...uri);
    return axios.post(url, JSON.stringify(data), {
      headers: { 'Content-Type': HTTP.CONTENT_TYPE.APPLICATION_JSON_UTF8 }
    }).then(resp => this.responseHandler(resp));
  }
}

export default Ajax;