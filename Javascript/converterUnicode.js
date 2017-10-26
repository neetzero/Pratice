var oriStr = 'abc`|d𣏝𣐥𣌦';

var unicodeArray = Array.prototype.map.call(oriStr, (x)=>{
  return '\\u' + x.charCodeAt(0).toString(16).toUpperCase().padStart(4,"0");
});
console.log(unicodeArray);

var str = unicodeArray.map((x)=>{
    var re = /^\\u/gi;
    x = x.replace(re, '');
    return String.fromCharCode(parseInt(x,16));
}).join('');

console.log(str);
