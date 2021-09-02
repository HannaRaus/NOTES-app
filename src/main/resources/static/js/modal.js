//<!-- script for sending id in modal "Delete anyway" button --!>
$('#deleteModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget) // Button that triggered the modal
  var noteId = button.data('note-id') // Extract info from data-* attributes
  var noteName = button.data('note-name')

  var href = '/note/delete?id=' + noteId // link forming

  var modal = $(this)
  modal.find('.modal-body p').text('Are you sure you want to delete note with title - ' + noteName + '?')
  $('.btn-danger', this).attr('href', href)
})