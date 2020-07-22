<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal fade" id="loadingModal"
     tabindex="-1" role="dialog" aria-labelledby="loadingModalLabel"
     data-backdrop="static" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content general-box-shadow-effect">
      <div class="modal-header bk-color general-box-shadow-effect">
        <h4 class="modal-title">Cyber Blogger</h4>
      </div>
      <div class="modal-body">
        <p class="text-secondary font-weight-bold">Processing......</p>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="basicInfoModal" tabindex="-1" role="dialog" aria-labelledby="basicInfoModalLabel"
     aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content general-box-shadow-effect">
      <div class="modal-header bk-color general-box-shadow-effect">
        <h5 class="modal-title" id="exampleModalLabel">Info</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p id="infoModal-show-info"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

