/**
 * 本地存储工具
 *
 * @type {{setSession: Storage.setSession, getSession: (function(*=): string), removeSession: Storage.removeSession, setLocal: Storage.setLocal, getLocal: (function(*=): string), removeLocal: Storage.removeLocal}}
 */
const Storage = {
  setSession: (key, value) => {
    sessionStorage.setItem(key, value);
  },
  getSession: key => sessionStorage.getItem(key),
  removeSession: key => {
    if (arguments.length === 0) {
      sessionStorage.clear();
    }
    sessionStorage.removeItem(key);
  },
  setLocal: (key, value) => {
    localStorage.setItem(key, value);
  },
  getLocal: key => localStorage.getItem(key),
  removeLocal: key => {
    if (arguments.length === 0) {
      localStorage.clear();
    }
    localStorage.removeItem(key);
  }
};

/**
 * 时间工具
 *
 * @type {{dateFormat: Time.dateFormat, dateTimeFormat: Time.dateTimeFormat}}
 */
const Time = {
  dateFormat: () => {

  },
  dateTimeFormat: () => {

  }
};

export { Storage, Time };
