import components.Slack

def call() {
    def slack = new Slack(this);
    DURATION = slack.getDurationString(new Date(env.BUILD_START), new Date());

    /* Red */
    color = "#e84118";
    slackMessage = "Build <${env.RUN_DISPLAY_URL}|#${env.BUILD_NUMBER}> failed";
    logMessage = "Build ${env.BUILD_NUMBER} failed";

    if (env.SUCCESS && env.SUCCESS == "true") {
        /* Green */
        color = "#44bd32";
        slackMessage = "Build <${env.RUN_DISPLAY_URL}|#${env.BUILD_NUMBER}> finished successfully";
        logMessage = "Build ${env.BUILD_NUMBER} finished successfully";
    }

    attachments = [
        [
            color: color,
            fields: [
                [
                    title: "Message",
                    value: slackMessage
                ],
                [
                    title: "Duration",
                    value: DURATION
                ]
            ],
        ]
    ];

    postAttachmentInThread(attachments);

    buildStartAttachment = getBuildStartAttachment();
    buildStartAttachment[0].color = "blue";
    buildStartAttachment[1].color = "blue";

    modifyBuildStartAttachment(attachment);
    echo(logMessage);

    return new Date();
}
