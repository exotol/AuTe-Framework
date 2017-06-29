(function($){
    $.fn.serializeObject = function(){

        var self = this,
            json = {},
            push_counters = {},
            patterns = {
                "validate": /^[a-zA-Z][a-zA-Z0-9_]*(?:\[(?:\d*|[a-zA-Z0-9_]+)])*$/,
                "key":      /[a-zA-Z0-9_]+|(?=\[])/g,
                "push":     /^$/,
                "fixed":    /^\d+$/,
                "named":    /^[a-zA-Z0-9_]+$/
            };


        this.build = function(base, key, value){
            base[key] = value;
            return base;
        };

        this.push_counter = function(key){
            if(push_counters[key] === undefined){
                push_counters[key] = 0;
            }
            return push_counters[key]++;
        };

        $.each($(this).serializeArray(), function(){

            // skip invalid keys
            if(!patterns.validate.test(this.name)){
                return;
            }

            var k,
                keys = this.name.match(patterns.key),
                merge = this.value,
                reverse_key = this.name;

            while((k = keys.pop()) !== undefined){

                // adjust reverse_key
                reverse_key = reverse_key.replace(new RegExp("\\[" + k + "\\]$"), '');

                // push
                if(k.match(patterns.push)){
                    merge = self.build([], self.push_counter(reverse_key), merge);
                }

                // fixed
                else if(k.match(patterns.fixed)){
                    merge = self.build([], k, merge);
                }

                // named
                else if(k.match(patterns.named)){
                    merge = self.build({}, k, merge);
                }
            }

            json = $.extend(true, json, merge);
        });

        return json;
    };
})(jQuery);

function restSaveForm(formSelector, stateSelector, url) {
    $(stateSelector).html('Saving...').css('color', 'orange');
    var json = $(formSelector).serializeObject();
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: contextPath + url,
        data: JSON.stringify(json[Object.keys(json)[0]]),
        success: function (data) {
            console.log('result:', data);
            $(stateSelector).html('Saved').css('color', 'darkgreen');
            setTimeout(function () {
                $(stateSelector).html('');
            }, 3000);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(xhr, ajaxOptions, thrownError);
            $(stateSelector).html('Error!').css('color', 'red');
        }
    });
}

$(function () {
    $.ajaxSetup({traditional: true});

    $('#save-steps').click(function () {
        restSaveForm('#steps-form', '#saving-state', '/rest/step/save');
    });

    $('#save-expected-service-requests').click(function () {
        restSaveForm('#expected-service-requests-form', '#save-expected-service-requests-state', '/rest/step/save-expected-service-requests');
    });

    $('[data-delete-expected-request]').click(function () {
        var expectedServiceRequestId = $(this).attr('data-delete-expected-request');
        if (confirm('Delete record?')) {
            $.post(contextPath + '/rest/step/delete-expected-request', { expectedServiceRequestId: expectedServiceRequestId }, function (data) {
                console.log('delete expected request data:', data);
                window.ttt = this;
            });
        }
    });
});
