var seckill = {
    url: {
        now:function() {
            return '/seckill/time';
        },
        exporter:function(seckillId) {
            return '/seckill/' + seckillId + '/exporter';
        },
        execution: function(seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    handleSeckillkill:function(seckillId, node){
        //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>').show();
        $.ajax({
            type:'post',
            url:seckill.url.exporter(seckillId),
            success:function(result) {
                if(result && result.success) {
                    var exporter = result.data;
                    if(exporter.exposed) {
                        $('#killBtn').one('click', function() {
                            $(this).addClass('disabled');
                            $.ajax({
                                url:seckill.url.execution(seckillId, exporter.md5),
                                type:'post',
                                success:function(result) {
                                    if(result && result.success) {
                                        var killResult = result.data;
                                        node.html('<span class="label label-success">' + killResult.stateInfo + '</span>')
                                    } else {
                                        console.info("result:" + result);
                                    }
                                }
                            });
                        })
                    } else {
                       seckill.countdown(seckillId, exporter.now, exporter.start, exporter.end);
                    }
                } else {
                    console.info("result:" + result);
                }
                node.show();
            },
        });
    },
    validatePhone:function(phone) {
        return phone && phone.length == 11 && !isNaN(phone);
    },
    countdown:function(seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        console.info(nowTime < startTime);
        if(endTime < nowTime) {
            seckillBox.html("秒杀结束");
        } else if(nowTime < startTime) {
            var killTime = new Date(startTime);
            seckillBox.countdown(killTime, function(event) {
                var format = event.strftime('秒杀倒计时:%D天 %H时 %M分 %S秒')
                seckillBox.html(format);
            }).on('finish.countdown', function()  {
                seckill.handleSeckillkill(seckillId, seckillBox);
            });
        } else {
            seckill.handleSeckillkill(seckillId, seckillBox);
        }
    },
    detail: {
        init :function(params) {
            var killPhone = $.cookie('killPhone');
            if(!seckill.validatePhone(killPhone)) {
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show:true,
                    backdrop:'static',
                    keyboard:false,
                });
                $("#killPhoneBtn").click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validatePhone(inputPhone)) {
                        $.cookie('killPhone', inputPhone, {expires:7, path:'/seckill'});
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide()
                            .html('<label class="label label-danger">手机号错误！</label>')
                            .show(300);
                    }
                });
            }

            $.ajax({
                url:seckill.url.now(),
                success:function(result) {
                    if(result && result.success) {
                        var now = result.data;
                        seckill.countdown(params.seckillId, now, params.startTime, params.endTime);
                    } else { console.info("result:" + result);}
                }
            });
        }
    }
};