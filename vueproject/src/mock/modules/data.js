// 引入随机函数
import { Random } from 'mockjs'
// 引入Mock
const Mock = require('mockjs')

// 定义数据
const listData = Mock.mock({
  'data|5': [{
    cardid: () => '12345',
    goodsId:() => '1',
    userId:() => 'root',
    price: () =>'10988.00',
    thumbnail: () =>' 艺术定制版 超光谱影像系统 创新双屏操作体验 P50宝盒',
    name: () =>'HUAWEI P50',
    num: () =>1
  },       
   {
     cardid: () => '12345',
        goodsId:() => '2',
        userId:() => 'root',
        price:() => '3299.00',
        thumbnail:() => '双3200万前置Vlog镜头 100W超级快充',
        name:() => 'HUAWEI nova 9 Pro',
        num:() => 2
      },
      {
        cardid: () => '12345',
        goodsId: () =>'3',
        userId: () =>'root',
        price:() => '3599.00',
        thumbnail:() => '超级快充 5000万超感光影像 双卡双待',
        name: () =>'荣耀V40 5G',
        num:() => 1
      },
      {
    cardid:() =>  '12345',
        goodsId: () =>'4',
        userId: () =>'root',
        price:() => '1599.00',
        thumbnail: () =>'7.45mm超薄设计 40W超级快充 6nm疾速芯',
        name:() => '荣耀Play6T Pro',
        num: () =>2
      },
      {
       cardid: () => '12345',
        goodsId: () =>'5',
        userId:() => 'root',
        price:() => '65.00',
        thumbnail:() => '舒适美观',
        name: () =>'Nick长裤',
        num:() => 4
      }]
})

function list (res) {
  // res是一个请求对象，包含: url, type, body
  return {
    code: 200,
    data: listData.data,
    message: '请求成功'
  }
}

export default { list }
