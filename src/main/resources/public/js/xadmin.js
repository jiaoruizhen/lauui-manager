window.config = {
    urlBase: '' //设定扩展的Layui模块的所在目录，一般用于外部模块扩展
};

$(function () {
    //加载弹出层

    layui.use(['form', 'element'],
        function () {
            layer = layui.layer;
            element = layui.element;
        });
    //触发事件
    var tab = {
        tabAdd: function (title, url, id) {
            //新增一个Tab项
            element.tabAdd('xbs_tab', {
                title: title
                ,
                content: '<iframe tab-id="' + id + '" frameborder="0" src="' + url + '" scrolling="yes" class="x-iframe"></iframe>'
                ,
                id: id
            })
        }
        , tabDelete: function (othis) {
            //删除指定Tab项
            element.tabDelete('xbs_tab', '44'); //删除：“商品管理”


            othis.addClass('layui-btn-disabled');
        }
        , tabChange: function (id) {
            //切换到指定Tab项
            element.tabChange('xbs_tab', id); //切换到：用户管理
        }
    };


    tableCheck = {
        init: function () {
        },
        getData: function () {
            var obj = $(".layui-form-checked").not('.header');
            var arr = [];
            console.log(obj);
            obj.each(function (index, el) {
                arr.push(obj.eq(index).attr('data-id'));
            });
            return arr;
        }
    };

    //开启表格多选
    tableCheck.init();


    $('.container .left_open i').click(function (event) {
        if ($('.left-nav').css('left') == '0px') {
            $('.left-nav').animate({left: '-221px'}, 100);
            $('.page-content').animate({left: '0px'}, 100);
            $('.page-content-bg').hide();
        } else {
            $('.left-nav').animate({left: '0px'}, 100);
            $('.page-content').animate({left: '221px'}, 100);
            if ($(window).width() < 768) {
                $('.page-content-bg').show();
            }
        }

    });

    $('.page-content-bg').click(function (event) {
        $('.left-nav').animate({left: '-221px'}, 100);
        $('.page-content').animate({left: '0px'}, 100);
        $(this).hide();
    });

    $('.layui-tab-close').click(function (event) {
        $('.layui-tab-title li').eq(0).find('i').remove();
    });

    $("tbody.x-cate tr[fid!='0']").hide();
    // 栏目多级显示效果

    //左侧菜单效果
    // $('#content').bind("click",function(event){
    $('.left-nav #nav li').click(function (event) {

        if ($(this).children('.sub-menu').length) {
            if ($(this).hasClass('open')) {
                $(this).removeClass('open');
                $(this).find('.nav_right').html('&#xe697;');
                $(this).children('.sub-menu').stop().slideUp();
                $(this).siblings().children('.sub-menu').slideUp();
            } else {
                $(this).addClass('open');
                $(this).children('a').find('.nav_right').html('&#xe6a6;');
                $(this).children('.sub-menu').stop().slideDown();
                $(this).siblings().children('.sub-menu').stop().slideUp();
                $(this).siblings().find('.nav_right').html('&#xe697;');
                $(this).siblings().removeClass('open');
            }
        } else {

            var url = $(this).children('a').attr('_href');
            var title = $(this).find('cite').html();
            var index = $('.left-nav #nav li').index($(this));

            for (var i = 0; i < $('.x-iframe').length; i++) {
                if ($('.x-iframe').eq(i).attr('tab-id') == index + 1) {
                    tab.tabChange(index + 1);
                    event.stopPropagation();
                    return;
                }
            }
            ;

            tab.tabAdd(title, url, index + 1);
            tab.tabChange(index + 1);
        }

        event.stopPropagation();

    })

});

var cateIds = [];

function getCateId(cateId) {

    $("tbody tr[fid=" + cateId + "]").each(function (index, el) {
        id = $(el).attr('cate-id');
        cateIds.push(id);
        getCateId(id);
    });
}

/*弹出层*/

/*
    参数解释：
    title   标题
    url     请求的url
    id      需要操作的数据id
    w       弹出层宽度（缺省调默认值）
    h       弹出层高度（缺省调默认值）
*/
function x_admin_show(title, url, w, h) {
    if (title == null || title == '') {
        title = false;
    }
    ;
    if (url == null || url == '') {
        url = "404.html";
    }
    ;
    if (w == null || w == '') {
        w = ($(window).width() * 0.9);
    }
    ;
    if (h == null || h == '') {
        h = ($(window).height() - 50);
    }
    ;
    layer.open({
        type: 2,
        area: [w + 'px', h + 'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: title,
        content: url
    });
}


/*关闭弹出框口*/
function x_admin_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

function cloneObj(obj, newObj) {
    if (!newObj)
        newObj = {};

    if (obj instanceof Array) {
        newObj = [];
    }
    for (let key in obj) {
        let val = obj[key];
        newObj[key] = typeof val === 'object' ? cloneObj(val) : val;
    }
    return newObj;
}

function cleanForm(o) {
    $(':input', o)
        .not(':button,:submit,:reset,:hidden')
        .val('')
        .removeAttr('checked');
    $('select', o).val('');
}

function timetrans(date) {
    var date = new Date(date);//如果date为13位不需要乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y + M + D + h + m + s;
}


function setStorage(key, obj) {
    sessionStorage.setItem(key, JSON.stringify(obj));
}

function getStorage(key) {
    return JSON.parse(sessionStorage.getItem(key));
}


function ajaxSend(obj) {
    let layer = obj.layui.layer;
    let table = obj.layui.table;
    let idx = layer.msg('加载中', {
        icon: 16
        , shade: 0.01
        , time: 0
    });
    $.ajax({
        type: obj.type,
        url: obj.url,
        async: obj.async ? false : true,
        data: obj.data,
        dataType: "json",
        success: function (res) {
            if (obj.success)
                obj.success(res);
            else
                defaultCallBack(res, table, layer);
            layer.close(idx);
        },
        error: function () {
            layer.msg("操作失败", {icon: 2});
            layer.close(idx);
        }
    });
}

function defaultCallBack(res, table, layer) {
    if (res.header.status === 1000) {
        if (table.name) {
            table.reload(table.name);
        }
        layer.msg("操作成功", {icon: 6});
    } else {
        layer.msg("操作失败", {icon: 2});
    }
}


function getTabChecked(table, layer, err) {
    let data = table.checkStatus(table.name).data;

    if (data.length == 0) {
        layer.msg(err, {icon: 2});
        return false;
    }
    let ids = '';
    data.forEach(function (item) {
        ids += item.id + ',';
    });
    return ids.substring(0, ids.length - 1);
}

function openWindow(obj) {
    top.editData = obj.data;
    let layer = obj.layui.layer;
    let table = obj.layui.table;
    layer.open({
        type: 2,
        area: obj.area,
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: obj.title,
        content: obj.url
        , btn: obj.btns
        , yes: function (index, layero) {
            top.editData.submit();
            top.editData.submitBack = function (res) {
                defaultCallBack(res, table, layer)
                layer.close(index);
            };
        }
        , btn2: function (index, layero) {
            layer.close(index);
        }
    });
}

function tableReload(table, field) {
    table.reload(table.name, {
        page: {
            curr: 1
        },
        where: field || null
    });
}


