import {getChannelName} from "../ajax/createWorkspaceRestController.js";

let respons = getChannelName();
let textP = $('#workspace_name_label');
textP.text("Tada! Meet your team’s first channel: #" + respons);


$(document).ready(function() {
    $("#tada-button").click(function() {
        window.location.href = "/chooseWorkspace";
    });
});

