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
            merge = this.value == "on" ? 1 : merge;

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

function restSaveForm(formSelector, stateSelector, url, successCallback) {
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
            console.log(successCallback);
            if (successCallback !== undefined) {
                successCallback();
            }
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

    $('#save-check-saved-values').click(function () {
        restSaveForm('#check-saved-values-form', '#save-check-saved-values-state', '/rest/step/save-check-saved-values');
    });

    $('#save-mock-service-response').click(function () {
        restSaveForm('#mock-service-response-form', '#save-mock-service-response-state', '/rest/step/save-mock-service-response');
    });

    $('[data-delete-expected-request]').click(function () {
        var expectedServiceRequestId = $(this).attr('data-delete-expected-request');
        var $this = this;
        if (confirm('Delete record?')) {
            $.post(contextPath + '/rest/step/delete-expected-request', { expectedServiceRequestId: expectedServiceRequestId }, function () {
                $($this).closest('tr').remove();
            });
        }
    });

    $('[data-delete-mock-service-response]').click(function () {
        var mockServiceId = $(this).attr('data-delete-mock-service-response');
        var $this = this;
        if (confirm('Delete record?')) {
            $.post(contextPath + '/rest/step/delete-mock-service-response', { mockServiceId: mockServiceId }, function () {
                $($this).closest('tr').remove();
            });
        }
    });

    $('[data-delete-check-saved-values]').click(function () {
        $(this).closest('div.form-group').remove();
    });


    $('#save-scenario-groups').click(function () {
        restSaveForm('#save-scenario-groups-form', '#save-scenario-groups-state', '/rest/project/save-scenario-groups');
    });

    $('#save-scenario-stands').click(function () {
        restSaveForm('#save-scenario-stands-form', '#save-scenario-stands-state', '/rest/project/save-stands');
    });
});

function cloneStep(stepId) {
    if (confirm('Confirm: Clone the step ' + stepId)) {
        // Save form
        restSaveForm('#steps-form', '#saving-state', '/rest/step/save', function () {
            // Clone step after save
            $.post(contextPath + '/rest/step/clone', { stepId: stepId }, function () {
                // Reload page after clone
                location.reload();
            });
        });
    }
}

function saveParameterSet(callback) {
    var parameterSetList = {};
    var parameterList = {};
    var stepId = 0;

    $('#parameter-set-form').find('input[data-sps-id]').each(function () {
        parameterSetList[$(this).attr('data-sps-id')] = $(this).val();
    });

    $('#parameter-set-form').find('input[data-sp-id]').each(function () {
        if (!parameterList[$(this).attr('data-sp-id')]) {
            parameterList[$(this).attr('data-sp-id')] = {};
        }
        parameterList[$(this).attr('data-sp-id')][$(this).attr('name')] = $(this).val();
    });

    $('#parameter-set-form').find('[data-sps-step-id]').each(function () {
        stepId = $(this).attr('data-sps-step-id');
    });

    var data = {
        stepId: stepId,
        parameterSetList: parameterSetList,
        parameterList: parameterList
    };
    console.log(data);

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: contextPath + '/rest/save-parameter-set-list',
        data: JSON.stringify(data),
        success: function (data) {
            console.log('result:', data);
            if (callback) {
                callback();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(xhr, ajaxOptions, thrownError);
        }
    });
}

function addStepParameter(stepId, parameterSetId) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: contextPath + '/rest/add-parameter',
        data: JSON.stringify({
            stepId: stepId,
            parameterSetId: parameterSetId
        }),
        success: function (data) {
            location.reload();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(xhr, ajaxOptions, thrownError);
        }
    });
}

function addStepParameterSet(stepId) {
    saveParameterSet(function () {
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: contextPath + '/rest/add-parameter-set',
            data: JSON.stringify({
                stepId: stepId
            }),
            success: function (data) {
                location.reload();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr, ajaxOptions, thrownError);
            }
        });
    });
}