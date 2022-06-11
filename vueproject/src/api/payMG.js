import axios from 'axios';
import { req } from './axiosFun';

// export function task_list_upload(Author, formData, tid, username, taskname) {
//     return request({
//       url: '/sys/file/upload?filename=' + taskname + '&tid=' + 1 + '&username=' + username,
//       method: 'post',
//       data: formData,
//       baseURL: 'http://localhost:8080/',
//       headers: {
//         'Authorization': Author,
//         'Content-Type': 'multipart/form-data; boundary=----WebKitFormBoundarypgnnfMj7vzrDwtRZ'
//       }
//       // params
//     })
//   }
  
export const task_list_upload = (formData) => {
  return axios.post("http://localhost:8080/upload",formData)
          .then(res => res.data)
};

export const register = (username, password) => {
  return axios.post("http://localhost:8080/user/register?username=" + username + "&password=" + password)
          .then(res => res.data)
};

export const login = (user) => {
  return axios.post("http://localhost:8080/user/login",user)
          .then(res => res.data)
};


export const getDataList = (params) => {
  return req("get","http://localhost:8080/sys/file/getFile",params)
};

export const getVideo = (params) => {
  return req("get","http://localhost:8080/sys/file/stream",params)
};

export const destroy = (params) => {
  return req("get","http://localhost:8080/sys/file/destroy",params)
};

export const getObject = (traffic,whether,dense,clime,picImprove,whImprove,draw) => {
  return axios.post("http://localhost:8080/getObject?traffic=" +traffic + "&whether=" + whether + "&dense=" + dense
  + "&clime=" + clime+ "&picImprove=" + picImprove + "&whImprove=" + whImprove + "&draw=" + draw)
  .then(res => res.data)
};

export const getObject2 = (count,time,height,fast,speed,parm1,parm2,place,resource,object1,object2,machineLearn,light) => {
  return axios.post("http://localhost:8080/getObject2?count=" +count + "&time=" + time + "&height=" + height
  + "&fast=" + fast+ "&speed=" + speed + "&parm1=" + parm1 + "&parm2=" + parm2 + "&resource=" + resource
  + "&place=" + place+ "&object1=" + object1+ "&object2=" + object2+ "&machineLearn=" + machineLearn+ "&light=" + light)
  .then(res => res.data)
};
