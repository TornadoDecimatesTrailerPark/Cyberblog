/**
 * Created by Chris on 10/02/20.
 *
 * @author Chris
 */
window.addEventListener('load', function () {
    let editor;
    const MYBLOGPAGEURL = './myblog';
    const uid = $("#hdnSessionUid").attr("data-value");
    ClassicEditor
        .create(document.querySelector('#modify-editor'), {
            toolbar: {
                items: [
                    'heading', '|', 'fontSize', 'fontFamily',
                    'alignment', 'bold', 'italic', 'link', 'bulletedList',
                    'numberedList', '|', 'indent', 'outdent', '|',
                    'imageUpload', 'blockQuote', 'insertTable', 'mediaEmbed',
                    'undo', 'redo', 'CKFinder'
                ]
            },
            language: 'en',
            image: {
                toolbar: ['imageTextAlternative', '|', 'imageStyle:alignLeft', 'imageStyle:full', 'imageStyle:alignRight'],
                styles: [
                    // This option is equal to a situation where no style is applied.
                    'full',
                    // This represents an image aligned to the left.
                    'alignLeft',
                    // This represents an image aligned to the right.
                    'alignRight'
                ]
            },
            table: {
                contentToolbar: [
                    'tableColumn', 'tableRow', 'mergeTableCells'
                ]
            },
            licenseKey: '',
            cloudServices: {
                tokenUrl: 'https://61119.cke-cs.com/token/dev/RAIWemO4gUt1n3UrtecRi462b2cGcDJeaTdCJ6499L5bddNZDKqd7QmMSbEY',
                uploadUrl: 'https://61119.cke-cs.com/easyimage/upload/'
            },
        })
        .then(newEditor => {
            editor = newEditor;
        })
        .catch(error => {
            console.error(error);
        });

    $('#submit-btn').click(function () {
        $("#article_extra_info").modal("show");
    });

    function getArticleAllInfo(articleAllInfo, articleMetadata) {
        constructArticleInfo(articleAllInfo, articleMetadata);
        let chooseTags = $("input[name='tag-group']:checked");
        articleAllInfo.tags = [];
        articleAllInfo.extraTypeList = [];
        articleAllInfo.counterTypeList = [];
        for (let i = 0; i < chooseTags.length; i++) {
            articleAllInfo.tags[i] = chooseTags[i].value;
        }
        articleAllInfo.postType = 0; // posted
        articleAllInfo.commentStatus = $('#select-comment-status').val();
        articleAllInfo.extraTypeList.push({
            "dictTypeId": 3,
            "dictTypeName": "readable range",
            "dictTypeVal": $('#select-view-type').val()
        });
        articleAllInfo.extraTypeList.push({
            "dictTypeId": 1,
            "dictTypeName": "18 prohibit",
            "dictTypeVal": $('#check-18-prevent').get(0).checked ? 0 : 1,
        });
        articleAllInfo.extraTypeList.push({
            "dictTypeId": 2,
            "dictTypeName": "original article",
            "dictTypeVal": $('#check-original').get(0).checked ? 0 : 1
        });
        articleAllInfo.counterTypeList.push({
            "dictTypeId": 6,
            "dictTypeName": "Forward",
            "dictTypeVal": 0
        });
        articleAllInfo.counterTypeList.push({
            "dictTypeId": 5,
            "dictTypeName": "Like",
            "dictTypeVal": 0
        });
        articleAllInfo.counterTypeList.push({
            "dictTypeId": 7,
            "dictTypeName": "Favourite",
            "dictTypeVal": 0
        });
        articleAllInfo.counterTypeList.push({
            "dictTypeId": 4,
            "dictTypeName": "Watch",
            "dictTypeVal": 0
        });
    }

    $('#submit-article-btn').click(function () {
        //get all article info
        let articleAllInfo = {};
        const articleMetadata = editor.getData();
        getArticleAllInfo(articleAllInfo, articleMetadata);
        $.ajax({
            type: "POST",
            url: "./submitArticle",
            data: JSON.stringify(articleAllInfo),
            dataType: "json",
            beforeSend: function () {
                $('#article_extra_info').modal('hide');
                $('#loadingModal').modal('show');
            },
            success: function (response) {
                $('#loadingModal').modal('hide');
                constructInfoModal(response);
                if (response.code === 1) {
                    // redirect to myBlog page
                    $(location).attr('href', MYBLOGPAGEURL + `?uid=${uid}`);
                }
                $('#basicInfoModal').modal('show');
            },
            error: function (response) {
                // show error modal
                console.info(response);
            }
        })
    });

    function constructInfoModal(item) {
        let ele = $("#infoModal-show-info");
        ele.html(item.message);
        if (item.code === 1){
            //success info
            ele.addClass("text-success");
        } else {
            // fail info
            ele.addClass("text-warning");
        }
    }

    function constructArticleInfo(articleInfo, editorData) {
        let tempNode = document.createElement("div");
        tempNode.innerHTML = editorData;
        articleInfo.title = tempNode.firstElementChild.innerHTML;
        let articleId = $("#hdArticleId").attr("data-value");
        articleInfo.id = parseInt(articleId);

        // lookup tempNode.childNodes to find nodes which we need
        let firstP = 0;
        tempNode.removeChild(tempNode.firstElementChild);
        tempNode.childNodes.forEach(function (item, index) {
            if (item.tagName === "P" && firstP === 0) {
                articleInfo.excerpt = item.innerText;
                firstP = index;
            }
        });
        //get userId from session
        articleInfo.authorId = uid;
        articleInfo.content = tempNode.innerHTML;
    }

    $("#save-btn").click(function () {
        // construct articleDto to back-side
        // ajax
        const editorData = editor.getData();

        let articleInfo = {};
        // find article id in order to decide editing or inserting for back-side
        constructArticleInfo(articleInfo, editorData);
        articleInfo.commentStatus = -1;
        articleInfo.postType = 1;
        //ajax save article
        $.ajax({
            type: "POST",
            url: "./saveArticle",
            data: JSON.stringify(articleInfo),
            dataType: "json",
            beforeSend: function(){
                $('#loadingModal').modal('show');
            },
            success: function (response) {
                $('#loadingModal').modal('hide');
                constructInfoModal(response);
                if (response.code === 1) {
                    $('#hdArticleId').attr('data-value', response.data);
                }
                $('#basicInfoModal').modal('show');
            },
            error: function (response) {
                // show error modal
                console.info(response);
            }
        })
    });

    function constructTags(response, tags) {
        let data = response.data;
        const tagsRow = $('#tags-row');
        tagsRow.empty();
        data.forEach(function (item, index) {
            let template = `<div class="form-check mb-2 mr-4">
                  <input class="form-check-input" name="tag-group" type="checkbox" id="check-tag-${index + 1}" value="${item.tagId}">
                  <label class="form-check-label" for="check-tag-${index + 1}">${item.tagName}</label>
                </div>`;
            let templateNode = document.createElement("div");
            templateNode.innerHTML = template;
            tagsRow.append(templateNode.firstElementChild);
        });
    }

    $('#article_extra_info').on('show.bs.modal', function () {
        //ajax get all tag dict and show
        $.ajax({
            type: "POST",
            url: "./common?method=getTagDicts",
            data: JSON.stringify([]),
            dataType: "json",
            success: function (response, tags) {
                constructTags(response, tags);
            },
            error: function (response) {
                console.info(response);
            }
        })

    });
    $('#basicInfoModal').on('hidden.bs.modal', function () {
        $('#infoModal-show-info').removeClass("text-success", "text-warning");
    })

});
