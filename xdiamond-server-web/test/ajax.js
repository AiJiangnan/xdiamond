import ajax from '../src/assets/js/tool/ajax';

ajax.get('/example', { msg: 'Hello world' }).then(data => console.log(data));

ajax.post('/example', { username: 'ajn', password: '1995' }).then(data => console.log(data));

ajax.postJson('/example/json', { version: 3, type: 3 }).then(data => console.log(data));

ajax.post('/example/json/{id}/{age}', null, 12, 24).then(data => console.log(data));

